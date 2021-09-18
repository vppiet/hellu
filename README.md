# Hellu

*Unsecure, unscalable, and thread-unsafe IRC bot implementation*

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

Every command belongs to a service which in turn is registered to the main object (Hellu).

## User Interface

Hellu has four command layers:

1. Prefix (`.`)
2. Service (e.g. `misc`)
3. Command (e.g. `hello`)
4. Parameters

For example, if a service identifies itself as `misc` and its command `hello` has no parameters, a valid input would
be `.misc hello` for triggering the message handler.

## Database Schema

`Primary key`
*Foreign key*

### footballcountry

| `code` | name |
| --- | --- |
| TEXT | TEXT |
| DK | Denmark

```
CREATE TABLE IF NOT EXISTS footballcountry(
	code TEXT PRIMARY KEY CONSTRAINT countrycode_length_and_upper CHECK (length(code) == 2 AND code == upper(code)),
	name TEXT
);
```

### footballseason

| id | name | type | countrycode | year | current | start | end |
| --- | --- | --- | --- | --- | --- | --- | --- |
| INTEGER | TEXT | TEXT | *footballcountry.code* | INTEGER | BOOLEAN | TEXT | TEXT |
| 4     | Euro Championship |  Cup | null | 2008 | false | 2008-06-07 | 2008-06-29

```
CREATE TABLE IF NOT EXISTS footballseason(
	id INTEGER PRIMARY KEY NOT NULL CHECK (id > 0),
	name TEXT NOT NULL,
	type TEXT NOT NULL,
	countrycode TEXT,
	year INTEGER NOT NULL CHECK (year > 1900),
	current BOOLEAN NOT NULL,
	start TEXT NOT NULL CONSTRAINT valid_date CHECK (start IS date(start, '+0 days')),
	end TEXT NOT NULL CONSTRAINT valid_date CHECK (end IS date(end, '+0 days')),
	FOREIGN KEY(countrycode) REFERENCES footballcountry(code)
);
```

### footballmeta

| resource | updated |
| --- | --- |
| `TEXT` | TEXT |
| footballcountry | 2021-17-09 |

```
CREATE TABLE IF NOT EXISTS footballmeta(
	resource TEXT PRIMARY KEY NOT NULL,
	updated TEXT NOT NULL CONSTRAINT valid_datetime CHECK (updated IS datetime(updated, '+0 seconds'))
);
```
