package org.calipsoide.featurevalves;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static reactor.core.publisher.Mono.just;

/**
 * Created by epalumbo on 9/16/17.
 */
@Service
public class YamlFileFeatureFactory {

    public Mono<Feature> read(FeatureFile file) {
        final String content = file.getBuffer().toString();
        final FeatureData data = new Yaml().loadAs(content, FeatureData.class);
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
        return just(new Feature(file.getId(), valves, evaluator, data.active));
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
