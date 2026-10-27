# 🧭 Waypoint Compass

A lightweight, vanilla-friendly navigation mod for **Minecraft 26.3** running on **Fabric (Java 25)**.

Instead of heavy minimaps or immersion-breaking coordinate overlays, **Waypoint Compass** gives you an in-world tool to mark, track, and cycle between locations directly from your hotbar.

---

## ✨ Features

- 📍 **Save Waypoints on the Fly**: Right-click to record your current coordinates (`X, Y, Z`) and dimension.
- 🔄 **Quick Cycle**: Shift + Right-click to cycle through up to **10 saved waypoints**.
- 🖥️ **Minimalist HUD Overlay**: Holding the compass displays the active waypoint name and live distance (`🧭 [Home Base] 142m`) right above the hotbar.
- ☠️ **Death Location Auto-Save**: Automatically saves your coordinates upon death so you can navigate straight back to your dropped items.
- 🌌 **Cross-Dimension Aware**: Gracefully indicates if a waypoint was saved in another dimension (e.g., Nether vs Overworld).
- ⚙️ **Configurable**: Easily adjust max waypoints, toggle the HUD, or disable death saving in `config/waypoint_compass.json`.

---

## 🛠️ Crafting Recipe

Crafted in a standard Crafting Table using **1 Vanilla Compass** surrounded by **4 Amethyst Shards**:

```
     [ Amethyst ]
[ Amethyst ] [ Compass ] [ Amethyst ]
     [ Amethyst ]
```

---

## 🎮 Controls

| Action | Control | Result |
|---|---|---|
| **Save Location** | `Right-Click` | Saves current coordinates as a new waypoint |
| **Cycle Waypoint** | `Shift + Right-Click` | Cycles to the next saved waypoint |
| **View Distance** | Hold in Main Hand / Off-Hand | HUD displays live tracking info |
| **Inspect Waypoint** | Hover in Inventory | Displays active waypoint, coordinates, and count |

---

## ⚙️ Configuration (`config/waypoint_compass.json`)

```json
{
  "maxWaypoints": 10,
  "saveDeathLocation": true,
  "showHudOverlay": true
}
```

---

## 📦 Tech Stack & Compatibility

| Specification | Version |
|---|---|
| **Minecraft** | `26.3` |
| **Java** | `Java 25 (LTS)` |
| **Mod Loader** | Fabric Loader `0.20.1` |
| **Fabric API** | `0.165.0+26.3` |
| **Environment** | Client & Server compatible |

---

## 🚀 Building from Source

```powershell
# Windows
.\gradlew.bat build

# Linux / macOS
./gradlew build
```
Compiled mod jar: `build/libs/waypoint-compass-1.0.0.jar`.

---

## 📄 License
Licensed under [CC0-1.0](LICENSE).
