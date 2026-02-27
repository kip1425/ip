# Anoop User Guide

Anoop is a desktop task-tracking chatbot for managing todos, deadlines, and events using fast text commands.

## Quick Start

1. Launch the app.
2. Type a command and press Enter.
3. Use `list` any time to see all tasks.
4. Use `bye` to exit.

## Command Format

- Task numbers are **1-based** (the first task is `1`).
- Date-time format for deadlines/events: `yyyy-MM-dd HHmm`
- Example date-time: `2026-01-29 2000`

## Features

### Add a todo
Adds a basic task without a date.

`todo <description>`

Example:
`todo read chapter 3`

### Add a deadline
Adds a task with a due date/time.

`deadline <description> /by <yyyy-MM-dd HHmm>`

Example:
`deadline submit report /by 2026-03-01 2359`

### Add an event
Adds a task with a start and end date/time.

`event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`

Example:
`event hackathon /from 2026-03-10 0900 /to 2026-03-10 1800`

### List tasks
Shows all tasks in your list.

`list`

### Mark task as done
Marks a task as completed.

`mark <task number>`

Example:
`mark 2`

### Unmark task
Marks a task as not completed.

`unmark <task number>`

Example:
`unmark 2`

### Delete task
Removes a task from the list.

`delete <task number>`

Example:
`delete 3`

### Find tasks by keyword
Shows tasks whose descriptions contain the keyword.

`find <keyword>`

Example:
`find report`

### Undo last change
Reverts the most recent undoable command (`todo`, `deadline`, `event`, `mark`, `unmark`, `delete`).

`undo`

If there is nothing to undo, Anoop replies with `Nothing to undo!`.

### Exit
Closes the app.

`bye`

## Error Handling

- Invalid command: Anoop reports an unknown command.
- Missing command arguments: Anoop reports what is missing.
- Invalid task number: task number must be an integer and within range.
- Invalid date-time: use `yyyy-MM-dd HHmm` (example: `2026-01-29 2000`).
