# AI Code Challenge #2 - Build a Breakout-style Game in Under 1 Hour

## Goal

Build a **Breakout-style** game - using an LLM to help you write all the code, step-by-step or in full.

The only editing that is allowed is _copy & paste_

<img src="https://robotwhale.wordpress.com/wp-content/uploads/2014/06/breakout.gif" alt="image">

## The Challenge

Build a Breakout game that supports:

- A ball that bounces off walls, paddle, and bricks
- A paddle controlled by the player
- Bricks that disappear when hit
- A simple life and scoring system
- _Optional_: Level progression
- _Optional_: Save high scores
- _Optional_: Add power-ups
- _Optional_: Add visual or audio plish

## Rules

1. Use an LLM as your coding partner - and let it write all the code.

You can only _copy & paste_ code (the whole file or parts of the file)

_Suggestion_: use one of the following:
- Microsoft Copilot
- Github Copilot
- ChatGPT

2. Pick any programming language that supports 2D games

Here are some popular choices:

| **Language** | **Suggested Library/Engine** |
|:-------------|:-----------------------------|
| C++          | SFML, SDL2                   |
| Rust         | ggez, Bevy                   |
| Python       | pygame, arcade, tkinter      |
| JavaScript   | Canvas API, Phaser.js, p5.js |
| C#           | Unity, MonoGame              |
| Go           | Ebiten                       |
| Lua          | LOVE2D                       |

3. Ask for help constantly

Don't write code manually - ask the LLM:

- "How do I draw a paddle in Phaser.js?"
- "Write idiomatic Rust code for all-paddle collision."

4. Think like a game designer

You focus on gameplay ideas. Let the LLM handle the implementation.

## Tips & Tricks

1. Work step-by-step.

Start with:

> "Create a basic game loop in [your language] that opens a window."

Then:

> "Add a ball that moves and bounces off window edges."

2. Ask for idiomatic code.

> "What's the cleanest way to write a class for a brick in Go?"

3. Break features into parts.

Use the [Breakout Feature List] to pick features and build them one at a time.

- Basic: bricks, paddle, ball
- Intermediate: scoring, levels, power-ups
- Advanced: AI paddle, dynamic bricks, endless mode

4. Ask for scaffolding.

> "Create a minimal Phaser.js project with a ball and paddle."
>
> "Set up a file structure for a LOVE2D game."

5. Refactor and optimize.

> "How can I refactor this into smaller functions?"
>
> "Does this follow best practices in this language?"

## Handy Resources

- [List of Game Libraries by Language](https://github.com/ellisonleao/magictools)
- [Breakout Feature List (Basic -> Advanced)](https://www.youtube.com/watch?v=Fy0aCDmgnxg)
- [Prompt Engineering Guide](https://www.promptingguide.ai)
- [How I use LLMs - Karpathy](https://www.youtube.com/watch?v=EWvNQjAaOHw)
- [Using LLMs to Help You Code - Simon Willison](https://simonwillison.net/2025/Mar/11/using-llms-for-code)

## Bonus Points

- Power-ups like lasers, multi-ball, sticky paddle
- Multiple levels or infinite mode
- Sound and visual polish
- Gamepad or mobile support
- AI paddle or bot player
- Add a level editor

