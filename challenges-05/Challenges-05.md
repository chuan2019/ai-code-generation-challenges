# AI Code Challenge #5 - App Development with Roo Code (VS Code Coding Agent)

## Goal

Get hands-on experience **working with Roo Code inside VS Code** by designing, coding, and iterating on a **small, self-contained application of your choice** - all while letting Roo Code's agentic modes (Architect, Code, Debug, Ask, etc.) guide and automate as much of the work as possible.

## TL;DR - What is Roo Code?
[Roo Code](https://marketplace.visualstudio.com/items?itemName=RooVeterinaryInc.roo-cline) is a free, open-source, model-agnostic autonomous coding agent that lives inside VS Code. Via chat-driven modes (Architect, Code, Debug, Ask, Orchestrator, and any custom ones you create) it can read & write multiple files, run terminal commands, automate a headless browser, and coordinate multi-step tasks - essentially acting like an AI teammate in your editor. If you'd like to see it in action, checkout the [Roo Code Tutorials](https://www.youtube.com/playlist?list=PL4byA17WHbJDbVggHuQrDACIQCHUwhNQm).

## The Challenge

1. **Choose (or invent) any small app idea.** _It can be CLI, GUI, web, game-tool, or script - as long as it's self-contained and finishable in a few hours._

2. Drive the entire workflow with **Roo Code**:

- Use **Architect Mode** to outline requirements & file structure.
- Use **Code Mode** to scaffold, refactor, and add features.
- Use **Debug Mode** to run tests/commands and fix issues.
- Switch to **Ask Mode** whenever you need explanations.

3. **Document your prompts & approvals** - note where Roo surprised you or needed a course-correction.

## Brainstorm Prompts

Ask yourself - or Roo Code in Ask Mode - questions like:

| **Domain**        | **Prompt to spark an idea**        |
|:------------------|:-----------------------------------|
| Personal utility | "What tiny app could automate an annoying daily task for me?" |
| Learning aids    | "Could I build a flash-card generator that scrapes notes and quizzes me?" |
| Fun/Creative     | "What micro-game could I prototype in 100 lines?" |
| Data Crunching   | "Could I parse a CSV and visualize one meaningful insight?" |

If nothing clicks, default to:
- **CLI To-Do App** - add/complete/delete tasks, optional file storage.
- **Goal-Setting Tracker** - create goals, log progress, show a simple dashboard.

## Rules

1. **Free-choice language & stack** - pick _any_ language or framework (bonus: something you rarely use).

2. **Use Roo Code as your coding pair** - rely on its modes & tools for:
- Scaffolding the project
- Editing multiple files
- Running commands/tests
- Generating docs or READMEs

3. **Keep approvals visible** - leave Roo's diff previews and checkpoint history intact.

4. **Reflect** - at the end, list the most useful prompts, biggest hurdles, and one improvement idea for Roo Code.

## Submission Checklist

- **Repo/ZIP** containing source, README, and (optional) screenshots or test reports
- **Prompt log** (copy/paste or screenshots) highlighting key interactions
- **Reflection**:
  - What Roo Code modes you used & why
  - Where Roo sped things up / tripped up
  - Next features you'd try with Roo

## Tips & Tricks for Working with Roo Code

1. **Start in Architect Mode**

> "Draft a minimal project plan for a CLI app that tracks jogging distances."

2. **Leverage multi-file edits**

> "Create `main.py`, `models.py`, and `storage.py` with the functions you outlined."

3. **Run & fix tests in Debug Mode**

> "Run `pytest` and resolve any failing cases."

4. **Stay in control - review diffs** before approving writes or commands.

5. **Auto-approve selectively** - consider auto-approving file _reads_ but keep writes/commands manual until you trust Roo on this project.

6. **Iterate** - when a feature works, ask Roo:

> "Refactor to idiomatic Rust / add docstrings /enforce PEP 8."

7. **Document effortlessly**

> "Generate a README that explains installation, usage, and future ideas."

## Bonus Ideas & Stretch Goals

| **Category**       | **Example** |
|:-------------------|:------------|
| **User Interface** | Add a minimal web UI with LiveReload (Roo can scaffold React/Vite or Flask with Jinja). |
| **Persistence**    | Store data in SQLite or a JSON file; ask Roo to write a migration script. |
| **Testing**        | Achieve > 80% test coverage - let Roo write tests and run them automatically. |
| **Automation**     | Use Roo's browser tool to run an end-to-end flow or take screenshots for documentation. |
| **Custom Mode**    | Define a new "UX Polish" mode that only edits CSS/HTML for styling tweaks. |

## Handy Resources

- [Roo Code **Docs & FAQ**](https://docs.roocode.com/) (modes, auto-approval, memory plugins)
- [Prompt Engineering Guide](https://promptingguide.ai)
