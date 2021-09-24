# Hellu

*Mostly unsecure, non-scalable, and thread-unsafe IRC bot implementation*

## Motivation

IRC is a messaging protocol which is used by numerous individuals and communities. Sharing external information in this
text-based chat system is usually done by copy-pasting the data or via URLs.

Currently, only the core architecture of IRC messaging is implemented, and interfaces to external APIs are in design
stage.

In the future, Hellu aims to target specific needs of a Finnish IRC channel by providing an interface for querying
external APIs. Particularly, football data (e.g. fixtures and live scores) is in the interest of this community.

## Name

The name Hellu is a nickname to a Finnish feminine name *Helena*.

## Architecture

The implementation is built around [Kitteh IRC Client Library (KICL)](https://kitteh.org/irc-client-library/) which
manages the IRC connection and messaging. By providing message handlers, the library can listen to specific commands
supplied by the users. Handlers are invoked asynchronously.

Every command belongs to a service which in turn is registered to the main object, Hellu. The basic architecture is as
follows:

- Hellu
	- KICL
	- Event listeners
	- ServiceManager
		- Service
			- Command

Attached to KICL, event listeners filters private and channel messages starting with predefined message properties (
defined in CommandProperties class). Messages (or other types of events such as joins) are forwarded via Hellu object
and passed on ServiceManager. ServiceManager delegates the messages further to Services which assess if the message
matches its own filters. As last consumers, commands handle the message.

## User Interface

Hellu has four command layers:

1. Prefix (`.`)
2. Service (e.g. `misc`)
3. Command (e.g. `hello`)
4. Parameters

For example, if a service identifies itself as `misc` and its command `hello` has no parameters, a valid input would
be `.misc hello` for triggering the message handler.

If only the service is called (e.g. `.misc`), the service replies with its description and a list of its commands (
e.g. `"Contains unrelated, miscellaneous stuff. Commands: hello"`).

## Database Schema

SQLite will be used in the future. Times would be kept in UTC timezone.

`Primary key`
*Foreign key*

### commandMatchObservation

| `service` | `command` | timestamp |
| --- | --- | --- |
| TEXT | TEXT | TEXT
| misc | hello | 2021-09-19T04:10:54.457070900Z |

```roomsql
CREATE TABLE IF NOT EXISTS commandMatchObservation(
	service TEXT NOT NULL,
	command TEXT NOT NULL,
	timestamp TEXT NOT NULL CONSTRAINT timestamp_is_valid_datetime CHECK (datetime(timestamp) IS NOT NULL)
);
```

### footballUpdated

| `resource` | updated |
| --- | --- |
| TEXT | TEXT |
| footballCountry | 2021-17-09 12:03:30 |

```roomsql
CREATE TABLE IF NOT EXISTS footballUpdated(
	resource TEXT PRIMARY KEY NOT NULL,
	updated TEXT NOT NULL CONSTRAINT updated_is_valid_datetime CHECK (updated IS datetime(updated, '+0 seconds'))
);
```

### footballCountry

| `countryCode` | name |
| --- | --- |
| TEXT | TEXT |
| DK | Denmark

```roomsql
CREATE TABLE IF NOT EXISTS footballCountry(
	countryCode TEXT PRIMARY KEY CONSTRAINT countryCode_length_and_upper CHECK (length(countryCode) = 2 AND code = upper(countryCode)),
	name TEXT
);
```

### footballSeason

| `id` | name | type | *countryCode* | year | current | start | end |
| --- | --- | --- | --- | --- | --- | --- | --- |
| INTEGER | TEXT | TEXT | TEXT | INTEGER | BOOLEAN | TEXT | TEXT |
| 4     | Euro Championship |  Cup | null | 2008 | false | 2008-06-07 | 2008-06-29

```roomsql
CREATE TABLE IF NOT EXISTS footballSeason(
	id INTEGER PRIMARY KEY NOT NULL CHECK (id > 0),
	name TEXT NOT NULL,
	type TEXT NOT NULL,
	countryCode TEXT,
	year INTEGER NOT NULL CHECK (year > 1900),
	current BOOLEAN NOT NULL,
	startTimestamp TEXT NOT NULL CONSTRAINT startTimestamp_is_valid_date CHECK (startTimestamp IS date(startTimestamp, '+0 days')),
	endTimestamp TEXT NOT NULL CONSTRAINT endTimestamp_is_valid_date CHECK (endTimestamp IS date(endTimestamp, '+0 days')),
	FOREIGN KEY(countryCode) REFERENCES footballCountry(countryCode)
);
```

## TODO

- KICL's thread cleanup
- Class privacy
