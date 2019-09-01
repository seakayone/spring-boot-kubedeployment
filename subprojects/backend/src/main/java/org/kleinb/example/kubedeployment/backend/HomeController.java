package org.kleinb.example.kubedeployment.backend;

import static java.time.Instant.now;

import io.vavr.control.Try;
import java.net.InetAddress;
import java.time.Instant;
import lombok.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

  private final BuildProperties buildProperties;

  public HomeController(BuildProperties buildProperties) {
    this.buildProperties = buildProperties;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<TimeVersionAndHostName> home() {
    return Mono.fromSupplier(TimeVersionAndHostName::new);
  }

  @Value
  private class TimeVersionAndHostName {
    private final Instant now = now();
    private final String hostname = Try
        .of(() -> InetAddress.getLocalHost().getHostName())
        .getOrElse("N/A");
    private final String version = buildProperties.getVersion();
    private final Instant buildtime = buildProperties.getTime();
  }
}
