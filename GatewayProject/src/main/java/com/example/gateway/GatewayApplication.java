package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ETUDIANT", r->r.path("/etudiant/**").uri("lb://ETUDIANT"))
                .route("HOLIDAYS", r -> r.path("/holidays/**").uri("lb://ETUDIANT"))   // ← ajout

                .route("RESERVATION", r->r.path("/reservation/**").uri("lb://RESERVATION"))
                .route("UNIVERSITY", r->r.path("/university/**").uri("lb://UNIVERSITY"))
                .route("BLOCCHAMBRE", r->r.path("/bloc/**").uri("lb://BLOCCHAMBRE"))
                .route("BLOCCHAMBRE", r->r.path("/chambre/**").uri("lb://BLOCCHAMBRE"))
                .route("FOYER", r->r.path("/foyer/**").uri("lb://FOYER"))

                .route("MAILING", r -> r
                        .path("/mailing/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:4000")
                )





                .build();
    }

}
