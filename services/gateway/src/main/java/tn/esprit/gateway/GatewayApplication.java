package tn.esprit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("EDUDIANT", r->r.path("/etudiant/**").uri("lb://EDUDIANT"))
				.route("RESERVATION", r->r.path("/reservation/**").uri("lb://RESERVATION"))
				.route("UNIVERSITY", r->r.path("/university/**").uri("lb://UNIVERSITY"))
				.route("BLOCCHAMBRE", r->r.path("/bloc/**").uri("lb://BLOCCHAMBRE"))
				.route("BLOCCHAMBRE", r->r.path("/chambre/**").uri("lb://BLOCCHAMBRE"))
				.route("FOYER", r->r.path("/foyer/**").uri("lb://FOYER"))
				.build();
	}

	@Bean
	public CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:5173");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}

}