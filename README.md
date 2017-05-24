# Description
SimpleAudio is a pure Java library which allows you to play WAV, AIFF, AU, OGG and MP3 audio files in a unified way.
Audio output can be streamed or buffered.
It is also possible to record audio from input devices with it.

The library was designed to be as easy as possible to use.
Code written with it, is highly readable and maintainable.

## Why should I use SimpleAudio? I mean there is already the Java Sound API.
The library itself is built on the Java Sound API.
It simplifies it to a degree where no knowledge about the classes `AudioSystem`,
`Clip`, `SourceDataLine`, `TargetDataLine` etc. is needed.
Plus, with this library you will be able to play MP3 and OGG files which is not possible
with the standard Java libraries.

## Features
- [x] Support for WAV, AU, AIFF, OGG and MP3 audio files
- [x] System depending support for AIFC and SND files
- [x] Reading audio file headers
- [x] A unified way of playing audio either buffered or streamed
- [x] The `Recorder` class which makes recording audio as easy as making coffee
- [x] Playing audio as a collection with the `Playlist` class
- [x] Listening to audio events

## Requirements
- Java 8 or higher

## Setup
Just put the `.jar` files on your class path.

# Links
See the [guide](https://github.com/RalleYTN/SimpleAudio/wiki) on how to use the libary
See the [roadmap](https://trello.com/b/a3o9JKrC)  
See the [online documentation](https://ralleytn.github.io/SimpleAudio/)  
See the [changelog](https://github.com/RalleYTN/SimpleAudio/blob/master/CHANGELOG.md)  
See the [download page](https://github.com/RalleYTN/SimpleAudio/releases)  