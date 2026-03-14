# SlowDown - Elytra Bounce Limiter

A lightweight Minecraft plugin designed for **Folia** and **Paper** servers to prevent "Elytra Bouncing" exploits, specifically on the Nether roof.

##  Features
* **Anti Elytra-Bounce:** Detects and limits high-speed movement when players attempt to "bounce" using Elytras on flat surfaces.
* **Smart Detection:** Only activates on the Nether roof (Y ≥ 127.5), ensuring that normal flight in the air remains unaffected.
* **Folia Ready:** Built using Folia's regional schedulers for maximum performance and compatibility.
* **Lightweight:** No heavy dependencies, minimal CPU impact.

##  Commands & Permissions
| Command                              | Description | Permission |
|--------------------------------------|-------------|------------|
| `/slowdown ELYTRA_JUMP <true/false>` | Toggle the limiter | `slowdown.admin` |
| `/slowdown reload`                   | Reload the configuration | `slowdown.admin` |

##  Installation
1. Download the latest `.jar` file.
2. Drop it into your server's `plugins` folder.
3. Restart the server or load the plugin.
4. Configure the settings in `config.yml` if needed.

##  Configuration
```yaml
jump:
  elytra-jump: false