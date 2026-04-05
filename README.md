# Kreditcart Observability Starter

Lightweight Spring Boot starter to enable **distributed tracing** and **log correlation** using OpenTelemetry.

---

## 🚀 Features

* Automatic **traceId / spanId generation**
* Log correlation via MDC
* OpenTelemetry integration
* OTLP exporter support (Jaeger / Grafana / etc.)
* Zero code changes required in microservices

---

## 📦 Installation

Build and install locally:

```bash
mvn clean install
```

Add dependency in your microservice:

```xml
<dependency>
    <groupId>com.kreditcart</groupId>
    <artifactId>kreditcart-observability-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

---

## ⚙️ Configuration

### application.properties (in consuming microservice)

```properties
otel.service.name=kreditcart-user-svc
otel.exporter.otlp.endpoint=http://localhost:4317
```

## 🐳 Run Jaeger (Local)

```bash
docker run --rm --name jaeger \
  -p 16686:16686 \
  -p 4317:4317 \
  -p 4318:4318 \
  -p 5778:5778 \
  -p 9411:9411 \
  cr.jaegertracing.io/jaegertracing/jaeger:2.17.0
```

Open UI:

```
http://localhost:16686
```

---

## 🧪 Verification

1. Start your service
2. Hit any API
3. Check logs:

```text
traceId=... spanId=...
```

4. Open Jaeger → select your service → view traces

---

## 🧠 How it works

```text
Request
  ↓
OpenTelemetry creates span
  ↓
MDC injects traceId/spanId
  ↓
Logs + traces are correlated
  ↓
Exporter sends data to Jaeger
```

---

## 📌 Notes

* No exporter needed for local log correlation
* Exporter required for Jaeger / distributed tracing
* Service name must be unique per microservice

---

## 🔮 Future Enhancements

* Metrics (Micrometer)
* Kafka tracing
* Async context propagation
* Sampling strategies

---