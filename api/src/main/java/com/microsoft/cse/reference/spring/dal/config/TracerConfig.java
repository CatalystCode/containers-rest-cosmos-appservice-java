package com.microsoft.cse.reference.spring.dal.config;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;


/**
 * Helper functions for tracing and logs
 */
@org.springframework.context.annotation.Configuration
public class TracerConfig {

    /**
     * Initializes a Jaeger tracer
     * @return the Jaeger tracer
     */
    @Bean
    public static Tracer initTracer() {
//        final SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv().withType("const").withParam(1);
//        final ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
//        final Configuration config = new Configuration(service).withSampler(samplerConfig).withReporter(reporterConfig);
//        return config.getTracer();

        SamplerConfiguration samplerConfig = new SamplerConfiguration()
                .withType(ConstSampler.TYPE)
                .withParam(1);
//        Configuration.SenderConfiguration senderConfig = new Configuration.SenderConfiguration()
////                .withAgentHost(config.getProperty("jaeger.reporter_host"))
////                .withAgentPort(Integer.decode(config.getProperty("jaeger.reporter_port")));
//                .withAgentHost("jaeger-agent.jaeger.svc.cluster.local")
//                .withAgentPort(5775);
        Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv();

        System.out.println("SenderConfig: " + senderConfig.toString());
        System.out.println("AgentHost: " + senderConfig.getAgentHost());
        System.out.println("AgentPort: " + senderConfig.getAgentPort());

        ReporterConfiguration reporterConfig = new ReporterConfiguration()
                .withLogSpans(true)
                .withFlushInterval(1000)
                .withMaxQueueSize(10000)
                .withSender(senderConfig);

        return new Configuration("springboot-api").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();


//        return new Configuration("springboot-api")
//                .withReporter(
//                        Configuration.ReporterConfiguration.fromEnv()
//                                .withLogSpans(true)
//                                .withFlushInterval(1000)
//                                .withMaxQueueSize(10000)
//                                .withSender(
//                                        Configuration.SenderConfiguration.fromEnv()
////                                        .withEndpoint("http://jaeger-collector:14268/api/traces")
////                                                Can only Endpoint or Agent.
//                                        .withAgentHost("jaeger-agent.jaeger.svc.cluster.local")
//                                        .withAgentPort(6832)
//                                )
//                )
//                .withSampler(
//                        Configuration.SamplerConfiguration.fromEnv()
//                                .withType(ConstSampler.TYPE)
//                                .withParam(1)
//                )
//                .getTracer();

    }
}