package org.calipsoide.featurevalves;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.toList;

/**
 * Created by epalumbo on 9/16/17.
 */
@Service
public class YamlFileFeatureService implements FeatureService {

    private FileRepository repository;

    @Autowired
    public YamlFileFeatureService(FileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Feature> findBy(ClientApplicationId applicationId, String name) {
        return repository
                .load(applicationId.toString(), name + ".yaml")
                .map(defaultCharset()::decode)
                .map(content -> new Yaml().loadAs(content.toString(), FeatureData.class))
                .map(data -> {
                    final HashingEvaluator evaluator =
                            new HashingEvaluator(Optional.ofNullable(data.eval).orElseGet(ImmutableList::of));
                    final List<FeatureValve> valves =
                            Optional.ofNullable(data.valves)
                                    .orElseGet(ImmutableList::of)
                                    .stream()
                                    .map(valve -> {
                                        final List<Tag> tags =
                                                Optional.ofNullable(valve.tags)
                                                        .orElseGet(ImmutableMap::of)
                                                        .entrySet()
                                                        .stream()
                                                        .map(entry -> new Tag(entry.getKey(), entry.getValue()))
                                                        .collect(toList());
                                        final ExpositionLevel exposition =
                                                Optional.ofNullable(valve.value)
                                                        .map(ExpositionLevel::ofPercentage)
                                                        .orElse(ExpositionLevel.ZERO);
                                        return new FeatureValve(valve.name, exposition, tags);
                                    })
                                    .collect(toList());
                    return new Feature(name, valves, evaluator, data.active);
                });
    }

    private static class FeatureData {

        public boolean active = true;
        public List<String> eval;
        public List<ValveData> valves;

        private static class ValveData {

            public String name;
            public Map<String, String> tags;
            public Integer value;

        }

    }

}
