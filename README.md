# Compose Preview kotlin-reflect regression
Minimal Compose app. `ReflectingPreview` feeds `@PreviewParameter` from
`SampleScenarios::class.members` (kotlin-reflect).
Works in **Otter 3 Feature Drop | 2025.2.3**.
Fails in **Quail 2 | 2026.1.2** and **Rabbit 1 | 2026.2.1 Canary 4**.
Regression of https://issuetracker.google.com/issues/240601093
## Reproduce
1. Open the project, sync, Make Project.
2. Open `MainActivity.kt` and show Compose Preview.
- `GreetingPreview` should render on every Studio version.
- `ReflectingPreview` should render `loading` and `error` in Otter 2025.2.3.
- In Quail 2026.1.2 / Rabbit 2026.2.1 Canary 4 it fails instead.
## Actual error
Failed to instantiate ...ReflectingPreviewParameterProvider parameter provider Caused by: layoutlib.internal.kotlin.jvm.KotlinReflectionNotSupportedError: Kotlin reflection implementation is not found at runtime. at layoutlib.internal.kotlin.jvm.internal.ClassReference.getMembers

`implementation("org.jetbrains.kotlin:kotlin-reflect")` is already on the module.
The same `::class.members` call works on a device.
## Workaround
Do not use kotlin-reflect in `PreviewParameterProvider`. Use `sequenceOf(...)`.