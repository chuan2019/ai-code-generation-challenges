# AI Code Challenge #4 - Build a simple real-time 3D renderer with a floating object and movable light

## Goal

Build a simple **real-time 3D renderer with a floating object and movable light** using an LLM to help you every step of the way.

(Bonus points if you do it in a programming language or framework you're unfamiliar with)

## The Challenge

**Build a minimal interactive 3D renderer that includes**:

- A sphere that floats up and down over time (e.g. with a sine wave)
- A point light source that affects the sphere's shading
- A way to **move the light interactively** (via gizmo, arrow keys, or UI sliders)

**Optional**:

- change light color or intensity
- Add mouse-based gizmos or sliders for light movement
- Display frames-per-second (FPS) or render mode toggle

## Rules

1. **Pick a language or framework** Examples:
- Rust + Bevy
- Go + Ebiten
- Python + moderngl/Ursina
- C++ + OpenGL
- JavaScript + Three.js, etc

2. **Use an LLM as your coding pair** Examples:
- GitHub Copilot
- ChatGPT, etc

3. **Focus on learning idiomatic patterns, syntax, and tooling**.

## Tips & Tricks

1. **Break it into steps**.

Ask: "_How do I render a basic sphere?_" -> "_How do I animate it?_" -> "_How do I add a point light?_"

2. **Ask for idiomatic code**.

Prompt: "_How do I create an animated 3D scene with lighting in Rust using Bevy?_"

3. **Ask for explanations**.

"_What does this uniform variable do?_", "_How does this light model work?_"

4. **Use interactive playgrounds or live templates**.

They let you avoid setup overhead.

- [Rust Playgound](https://play.rust-lang.org/?version=stable&mode=debug&edition=2024)
- [Go Playground](https://go.dev/play/)
- [Three.js Editor](https://threejs.org/editor/)
- [Replit](https://replit.com) for Python/JS/C++

5. **Ask for project scaffolding**.

Prompt: "_Create a minimal Bevy project with a scene, camera, and light._"

6. **Refactor and review**.

Prompt: "_Can you refactor this to follow idiomatic JS/G0/Rust style?_"

7. **Translate from a known language**.

Write the renderer in a language you know, and ask the LLM to translate it.

## Handy Resources

- [How I Use LLMs - Andrej Karpathy (YouTube)](https://www.youtube.com/watch?v=EWvNQjAaOHw)
- [Using LLMs to Help You Code - Simon Willison](https://simonwillison.net/2025/Mar/11/using-llms-for-code)
- [Prompt Engineering Guide](https://www.promptingguide.ai)

## Bonus Points

- Add a UI to control light position, color, or toggle shadows
- Implement a true gizmo (e.g. Three.js `TransformControls`)
- Support multiple lights
- Add a second animated object (e.g. rotating cube or orbiting moon)
- Use shaders to create custom lighting or surface effects