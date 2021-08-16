# Hellu

*Insecure, static, thread-unsafe IRC bot implementation*

## Motivation

IRC is a messaging protocol which is used by numerous individuals and communities. Sharing external information in this
text-based chat system is usually done by copy-pasting the data or via URLs.

Hellu targets specific needs of a Finnish IRC channel by providing an interface for querying external APIs.
Particularly, football data (e.g. fixtures and live scores) is in the interest of this community.

## Name

The name Hellu is a nickname to a Finnish feminine name *Helena*.

## Architecture

The implementation is built around [Kitteh IRC Client Library (KICL)](https://kitteh.org/irc-client-library/) which
manages the IRC connection and messaging. By providing message handlers, the library can listen to specific commands
supplied by the users. Handlers are invoked asynchronously.

Hellu uses SQLite for data persistence (WIP).

### Main Classes

- `Hellu`
    - Ties all functionality by composition
    - Initializes external connections
- `BotService`
    - Represents a specific subject
    - Manages IRC commands
- `BotCommand`
    - Represents a function invoked by user input

## Used Protocols

- IRC
- SQL
- HTTP

## User Interface

Hellu has four command layers:

1. Prefix (`.`)
2. Service (e.g. `football`)
3. Command (e.g. `live`)
4. Parameters

For example, if a service identifies itself as `football` and its command `live` has no parameters, a valid input would
be `.football live` for triggering the message handler.

Although possible, the need for more commands (3.) is seen too complex and not really usable from a user viewpoint.