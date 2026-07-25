# Tasks - Phase 16: Security & Offline Sync

- `[x]` Update Dependencies in `libs.versions.toml` (SQLCipher & SQLite KTX)
- `[x]` Implement `:core:security` Key Management
    - `[x]` `KeyStoreManager` (Database Key Generation)
- `[x]` Update Room Entities for Sync
    - `[x]` Add `lastUpdated` and `isDirty` to `MedicineEntity`, `ReportEntity`, `AppointmentEntity`
- `[x]` Configure Encrypted Room Database
    - `[x]` Update `MediAIDatabase`
    - `[x]` Configure `SupportFactory` in `DatabaseModule`
- `[x]` Implement Offline Sync Engine (`:core:data`)
    - `[x]` `SyncWorker` (WorkManager)
    - `[x]` `SyncManager` (Orchestrator)
- `[x]` Update Repositories to trigger Sync
- `[ ]` Verify Database Encryption and Background Sync
