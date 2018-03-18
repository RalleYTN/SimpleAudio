[![Build Result](https://travis-ci.org/RalleYTN/SimpleAudio.svg?branch=master)](https://travis-ci.org/RalleYTN/SimpleAudio)
[![Coverage Status](https://coveralls.io/repos/github/RalleYTN/SimpleAudio/badge.svg?branch=master)](https://coveralls.io/github/RalleYTN/SimpleAudio?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/87a04f7e6823474a83b49daf6acc6e23)](https://www.codacy.com/app/ralph.niemitz/SimpleAudio?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=RalleYTN/SimpleAudio&amp;utm_campaign=Badge_Grade)

# Description

SimpleAudio is a pure Java library which allows you to play WAV, AIFF, AU, OGG and MP3 audio files in a unified way.
Audio output can be played streamed or buffered.
It is also possible to record audio from input devices with it.
The library was designed to be easy to use.
Code written with it, is highly readable and maintainable.

## Changelog

### Version 2.0.1 (incompatible with older versions of this library)

- Made the project Maven compatible
- Renamed `Utils` to `Util`
- Added Unit-Tests
- Moved everything static in the `Audio` interface to `AbstractAudio`
- Fixed a bug that caused `BufferedAudio.isPlaying()` to return a wrong value
- Fixed a bug that would re-open an instance of `StreamedAudio` if it was closed in the `REACHED_END` event
- Added a close method in the `Playlist` class
- Fixed most threading issues in `StreamedAudio`

### Version 2.0.0 (incompatible with older versions of this library)

- Made the project modular for Java 9 (should still be compatible with Java 8 and below though; not tested)
- Added some more documentation
- Created the package `de.ralleytb.simple.audio.internal` and moved the `Utils` and `VorbisInputStream` classes in it

### Version 1.2.2

- Added the method `Recorder.start(File)`.
- Added the method `Audio.getDefaultAudioFormat()`.
- The `Recorder` class now uses the AU file format because it doesn't need a pre-defined length.
- Replaced the `ogg-vorbis.jar` with `j-ogg.jar`.

### Version 1.2.1

- Found a more elegant solution for reading the OGG headers.

### Version 1.2.0

- Added the ability to read headers of OGG files.
- Added three new audio event types.
- Audio events now also have attributes for old and new value.
- Added the method `Playable.isPaused()`. (could cause incompatibility with older versions)
- Added a `PlaylistListener`.

### Version 1.1.0

- Removed the `RecordingListener` interface. (causes incompatibility with older versions)
- Removed the `dispose` method from `Recorder`. (causes incompatibility with older versions)
- Added a new `start` method to `Recorder` which takes in an `OutputStream` on which the recorded data will be written.
- Added the method `Audio.getAudioInputStream(URL)`.
- Added some system specific file formats.
- Added the class `Playlist`.
- Added the interface `Playable` and moved some methods from `Audio` to there.
- Fixed some odd behavior with the `StreamedAudio.resume()` method.
- Fixed weird control behavior.
- The constructors of `StreamedAudio` and `BufferedAudio` now all throw an `AudioException`. (causes incompatibility with older versions)
- Added the method `Audio.getHeaders()`. This works for all supported formats except `FileFormat.OGG`.
- Added the method `Audio.isOpen()`.
- Added the `AudioListener` class.
- Removed the `Audio.ends()` method. You should use an `AudioListener` instead. (causes incompatibility with older versions)

### Version 1.0.0

- Release

## License

```
MIT License

Copyright (c) 2017 Ralph Niemitz

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Links

- [Wiki](https://github.com/RalleYTN/SimpleAudio/wiki)
- [Online Documentation](https://ralleytn.github.io/SimpleAudio/)
- [Download](https://github.com/RalleYTN/SimpleAudio/releases)
- [Java 8 Compatible Version](https://github.com/RalleYTN/SimpleAudio/tree/java8)

## Dependencies

- [JOgg 1.0.0](http://www.j-ogg.de/)
- [JLayer 1.0.1](http://www.javazoom.net/javalayer/sources.html)
- [MP3 SPI 1.9.5](http://www.javazoom.net/mp3spi/mp3spi.html)
- [tritonus 0.3.6](http://www.tritonus.org/)