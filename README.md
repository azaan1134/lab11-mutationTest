\# Лаборатори №11: Mutation testing — PIT



\*\*Овог нэр:\*\* С. Азжаргад

\*\*Оюутны код:\*\* B232270052



\### Орчин



```

> java -version

openjdk version "17.0.20.1" 2026-08-18

OpenJDK Runtime Environment Temurin-17.0.20.1+1 (build 17.0.20.1+1)

OpenJDK 64-Bit Server VM Temurin-17.0.20.1+1 (build 17.0.20.1+1, mixed mode, sharing)

```



```

> mvn -version

Apache Maven 3.9.16 (2bdd9fddda4b155ebf8000e807eb73fd829a51d5)

Maven home: C:\\Program Files\\apache-maven-3.9.16

Java version: 17.0.20.1, vendor: Eclipse Adoptium, runtime: C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.20.101-hotspot

Default locale: en\_US, platform encoding: Cp1252

OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"

```



\---



\## 1. Эхний хэмжилт (Starter)



\### JaCoCo



!\[JaCoCo starter](images/jacoco-start.png)



\- Line coverage: \*\*100% (25/25)\*\*

\- Branch coverage: \*\*92% (38/41)\*\*

\- Түүхий гаралт: \[`results/jacoco-start.csv`](results/jacoco-start.csv), \[`results/mvn-verify-start.txt`](results/mvn-verify-start.txt)



\### PIT



!\[PIT starter](images/pit-start.png)



\[`results/pit-start.txt`](results/pit-start.txt)-ээс:



```

>> Line Coverage (for mutated classes only): 25/25 (100%)

>> Generated 63 mutations Killed 18 (29%)

>> Mutations with no coverage 0. Test strength 29%

```



\### Ажиглалт



JaCoCo кодын бүх мөрийг (25/25) тестээр ажилласан гэж харуулж байгаа ч PIT-ийн 63 мутантаас ердөө 18-ыг нь (29%) тестүүд барьсан. Тестүүд кодыг \*ажиллуулж\* байгаа боловч гарсан утгыг нь \*шалгахгүй\* байгаа нь үүний шалтгаан: `assertNotNull`, `assertTrue(0..100)` гэх мэт сул assertion нь буруу утга буцсан ч давж гардаг. Тиймээс хамралт 100% байх нь тест алдаа барьж чадна гэсэн үг биш.

