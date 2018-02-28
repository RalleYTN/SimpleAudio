# Changelog
## Version 2.0.0
- Made the project modular for Java 9 (should still be compatible with Java 8 and below though; not tested)
- Added some more documentation
- Created the package "de.ralleytb.simple.audio.internal" and moved the "Utils" and "VorbisInputStream" classes in it

## Version 1.2.2
- Added the method `Recorder.start(File)`.
- Added the method `Audio.getDefaultAudioFormat()`.
- The `Recorder` class now uses the AU file format because it doesn't need a pre-defined length.
- Replaced the `ogg-vorbis.jar` with `j-ogg.jar`.
asasdjadja

## Version 1.2.1
- Found a more elegant solution for reading the OGG headers.

## Version 1.2.0
- Added the ability to read headers of OGG files.
- Added three new audio event types.
- Audio events now also have attributes for old and new value.
- Added the method `Playable.isPaused()`. (could cause incompatibility with older versions)
- Added a `PlaylistListener`.

## Version 1.1.0
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

## Version 1.0.0
- Release