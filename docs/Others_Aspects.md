# Aspectos Adicionales de Evaluación Técnica

Este documento complementa el flujo de trabajo destacando aspectos exigidos en la evaluación técnica orientados a la profesionalidad del código y el realismo.

## 1. Integración de Mocks Realistas (Contexto Panini)
En lugar de presentar datos vacíos, datos aleatorios, o cadenas como "Lorem Ipsum", el `MockEngine` fue precargado intencionalmente con incidentes **contextualizados en el ecosistema operativo de Panini**:
- *Faltante de cajas de sobres* en distribuidoras reales.
- *Problemas de control de calidad* por sobres mal sellados desde las imprentas.
- *Retrasos logísticos* regionales provocados por compañías de transporte (ej. "Logística Express").

Esto demuestra un análisis empresarial realista: la PoC está verdaderamente diseñada con base en las problemáticas logísticas del Mundial FIFA 2026.

## 2. API Contracts en YAML
En la carpeta raíz `/contracts` se diseñó la integración futura hacia un Backend RESTful. En lugar de un solo archivo gigantesco, la arquitectura API se estructuró dividiendo los contratos en múltiples archivos `.yaml` con granularidad alta (ej. un archivo para Listado, uno para Creación, otro para Status) para asegurar el máximo grado de modularidad y mantenibilidad técnica para otros desarrolladores.

## 3. Estado Limpio (Clean Repo)
El repositorio de entrega se mantiene limpio y exclusivamente con los archivos necesarios para el proyecto (carpetas de `app/`, `contracts/`, `docs/`, `video/`). No hay presencia de código basura o ramas inestables.
