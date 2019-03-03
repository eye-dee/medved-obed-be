package com.igor.medvedobedbe.controller;

import com.igor.medvedobedbe.controller.model.Metric;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metrics")
public class MetricsController {

  private static final Map<String, List<Double>> METRICS = new ConcurrentHashMap<>();

  @PostMapping
  public ResponseEntity sendMetric(@Valid @RequestBody Metric metric) {
    METRICS.merge(metric.getName(), Collections.singletonList(metric.getValue()), (v1, v2) -> Stream
        .of(v1, v2)
        .flatMap(List::stream)
        .collect(Collectors.toList()));

    return ResponseEntity.accepted().build();
  }

  @GetMapping("{name}/offset/{offset}")
  public List<Double> requestMetric(@PathVariable String name, @PathVariable Integer offset) {
    List<Double> metrics = METRICS.getOrDefault(name, Collections.emptyList());

    if (metrics.size() < offset) {
      return metrics;
    } else {
     return metrics.subList(metrics.size() - offset, metrics.size());
    }
  }
}
