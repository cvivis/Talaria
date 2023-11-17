# Talaria

이 저장소는 Talaria의 기술 문서, 포팅 매뉴얼, dump data 등을 기록하는 공간입니다.

---
# Porting Manual

## Introduce

This document provides guidelines for porting Talaria to a new environment.

## Hardware Requirements

- server based on Linux for using data plane
- server based on Linux for using control plane

## Software Requirements

- Docker 24.0.5

## Data Plane Server Setting

1. Change Directory to data-plane folder
2. Get SSL/TLS certificates for Data Plane
   
    ```bash
    make setup
    ```
    
3. Start Talaria Data Plane
   
    ```bash
    make data-plane
    ```

## Control Plane Server Setting

1. Change Directory to control-plane folder
2. Get SSL/TLS certificates for Control Plane
   
    ```bash
    make setup
    ```
    
3. Start Talaria Control Plane
   
    ```bash
    make control-plane
    ```
    
## Time Schedule and Budget

- EstimatedTime : 3 minutes
- Budget : 2 servers

## Version and Change history
| version | date |
| --- | --- |
| latest | 2023.11.17 |

---

## 링크 바로가기

- [scripts](scripts/README.md)
