# AI Code Challenge #6 - Update in game ImGui Menus

## Goal

Give developers **practical, code-level experience customizing Dear ImGui menus** in a standalone sample app - while letting **GitHub Copilot / ChatGPT** shortcut exploration, explanation, and routine edits.

## The Challenge

1. Locate real ImGui call-sites in the game.


## Reference Material

| **Purpose**               | **Link**            |
|:--------------------------|:--------------------|
| **ImGui source**          | https://github.com/ocornut/imgui |
| **Getting Started wiki**  | https://github.com/ocornut/imgui/wiki/Getting-Started |
| **ImGui Test Engine (automation examples)** | https://github.com/ocornut/imgui_test_engine |
| **Issue #6478 (practical menu samples)** | https://github.com/ocornut/imgui/issues/6478 |

## Rules
1. **Use Github Copilot/ChatGPT liberally** for boilerplate, doc comments, and Q&A
2. **Touch at least one data item** - read, write, or toggle it via your new menu item.

## Tips & Tricks
1. **Leverage the ImGui Demo window** (`ImGui::ShowDemoWindow`) to copy/paste widgets https://github.com/ocornut/imgui_test_engine.

2. **Search by symbol** in MS Visual Studio (`Ctrl + Shift + F`) to jump across ImGui usages fast.

3. **Read the comments** atop `imgui_common.cpp` - they explain our custom helpers.