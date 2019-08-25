package org.kleinb.example.kubedeployment.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping
  public TimeAndHostName home() {
    return new TimeAndHostName();
  }
}

@Value
class TimeAndHostName {

  private final Instant now = Instant.now();
  private final String hostname = hostname();

  private static String hostname() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException uhe) {
      return "unknown";
    }
  }
}

