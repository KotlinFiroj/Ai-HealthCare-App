# Tasks - Phase 18: CI/CD & Deployment

- `[x]` Refactor `android.yml` CI Pipeline
    - `[x]` Split into `lint`, `test`, and `build` jobs
    - `[x]` Add JaCoCo report artifact upload
- `[x]` Implement `release.yml` CD Pipeline
    - `[x]` Define tag-based trigger (`v*`)
    - `[x]` Add Build Release job
    - `[x]` Add Firebase App Distribution step (placeholder)
- `[x]` Create Release Scripts
    - `[x]` `scripts/versioning.sh`
- `[x]` Update Build Logic for Signing
    - `[x]` Modify `AndroidApplicationConventionPlugin.kt`
- `[x]` Configure GitHub Action Secrets Documentation
- `[x]` Verify CI Pipeline Syntax
