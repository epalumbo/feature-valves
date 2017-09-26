# Feature Valves

Feature flag HTTP server, backing feature state on Git repositories. Well suited for incremental rollouts, hence the term "valve".

### What is a "feature valve"?
A feature valve is a convenient way to call a feature flag that is able to be computed on each request. For any given request data in the form of a set of key-value pairs, the valve can compute if the feature flag is ON or OFF based on a list of predefined rules. These rules specify when they can be applied for the incoming request data, what level of exposition is allowed for the matching requests, and what value data must be considered in order to compare the incoming request against the exposition level.

### How to use it?
TODO - This service can be easily containerized (e.g. using Docker), and it can be deployed side-by-side with its client application server to be locally called, or as an ordinary remote service if latency is not an issue.
