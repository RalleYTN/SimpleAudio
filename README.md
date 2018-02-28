# Description

SimpleAudio is a pure Java library which allows you to play WAV, AIFF, AU, OGG and MP3 audio files in a unified way.
Audio output can be played streamed or buffered.
It is also possible to record audio from input devices with it.


The library was designed to be easy to use.
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
- [x] Recording audio
- [x] Playing audio as a collection with the `Playlist` class
- [x] Listening to audio events

## Requirements

- Java 8 or higher

## Setup

### Java 9 and higher

- Put all the JARs in your module path
- Make your module require `de.ralleytn.simple.audio`
- Start coding

### Java 8 and below

- Put all the JARs in your class path
- Start coding

# Links

- [How to Use](https://github.com/RalleYTN/SimpleAudio/wiki)
- [Online Documentation](https://ralleytn.github.io/SimpleAudio/)
- [Changelog](https://github.com/RalleYTN/SimpleAudio/blob/master/CHANGELOG.md)
- [Download Latest](https://github.com/RalleYTN/SimpleAudio/releases)
- [Download Java 8 Version](https://github.com/RalleYTN/SimpleAudio/releases/download/1.2.2/simple-audio_v1.2.2.zip)

# Dependencies

- [JLayer 1.0.1](http://www.javazoom.net/javalayer/sources.html)
- [MP3 SPI 1.9.5](http://www.javazoom.net/mp3spi/mp3spi.html)
- [tritonus](http://www.tritonus.org/)