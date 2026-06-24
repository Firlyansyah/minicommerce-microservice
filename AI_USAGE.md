## Tool AI yang Digunakan

- ChatGPT
- DeepSeek

## Bagian yang Dibantu AI

- Membantu memahami error Spring Boot.
- Membantu membuat struktur folder best practice.
- Membantu membuat Exception Handler aplikasi.
- Membantu membuat RestClient untuk komunikasi antarservice.
- Membantu refactor kode agar lebih rapi.
- Membantu setup docker compose.
- Membantu membuat validation, paging, search pada endpoint yang dibuat.
- Membantu memahami cara penggunaan JPA Repository dan JPA specification.
- Membantu memahami cara pembuatan API Gateway.
- Membantu memahami cara setup Swagger.
- Membantu membuat file README.md untuk dokumentasi aplikasi.

## Prompt Penting yang Digunakan

Beberapa contoh prompt yang saya gunakan selama pengembangan aplikasi:

```
Saya membuat api get all untuk catalog service dan order service. Bagaimana cara menerapkan search, filter, dan pagination?
```

```
Berdasarkan controller untuk catalog service dan order service. Bagaimana cara setup swagger untuk dokumentasi API?
```

```
Berdasarkan order service dan catalog service. Bagaimana cara menerapkan API Gateway untuk kedua service tersebut?
```

```
Saya telah membuat catalog service, order service, dan api gateway service. Bagaimana cara setup docker compose untuk ketiga service tersebut?
```

## Modifikasi yang Dilakukan Sendiri

- Menyesuaikan nama package.
- Menyesuaikan endpoint sesuai requirement.
- Menambah custom exception.
- Menyesuaikan type entity dengan requirement.
- Menambah validasi parameter pada API.
- Menguji ulang menggunakan Postman.

## Bagian yang Sudah Dipahami

- Memahami perbedaan Entity dan DTO.
- Memahami perbedaan service dan serviceImpl.
- Memahami cara kerja exception.
- Memahami order-service tidak boleh query langsung ke database catalog.
- Memahami create order harus mengurangi stok melalui API catalog-service.
- Memahami bahwa stack trace error lebih baik tidak dimunculkan ketika terjadi request error.
- Memahami cara penggunaan JPA Repository.
- Memahami cara penggunaan Lombok.
- Memahami fungsi API Gateway.
- Memahami cara setup swagger untuk dokumentasi API.
- Memahami cara setup docker compose untuk dockerization aplikasi.

## Bagian yang Masih Membingungkan

-
