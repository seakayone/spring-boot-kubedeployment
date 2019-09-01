package org.kleinb.example.kubedeployment.backend;

import static java.time.Instant.now;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import java.net.InetAddress;
import java.time.Instant;
import lombok.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

  private final BuildProperties buildProperties;
  private final GitProperties gitProperties;

  public HomeController(BuildProperties buildProperties,
      GitProperties gitProperties) {
    this.buildProperties = buildProperties;
    this.gitProperties = gitProperties;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<TimeVersionAndHostName> home() {
    return Mono.fromSupplier(TimeVersionAndHostName::new);
  }

  @Value
  private class TimeVersionAndHostName {

    Instant now = now();
    String hostname = Try
        .of(() -> InetAddress.getLocalHost().getHostName())
        .getOrElse("N/A");
    Map<String, Object> build = HashMap.of(
        "version", buildProperties.getVersion(),
        "buildtime", buildProperties.getTime(),
        "commit", gitProperties.getShortCommitId(),
        "branch", gitProperties.getBranch()
    );
  }
}
