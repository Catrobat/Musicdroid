/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.musicdroid.pocketmusic.soundplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import org.catrobat.musicdroid.pocketmusic.note.NoteName;

import java.util.HashMap;




public class SoundPlayer {
    private static final int PRIORITY = 1;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    private Context context;
    private HashMap<Integer, PlayThread> threadMap = null;
    private int maxStreams = 90;
    private int srcQuality = 100;

    @SuppressLint("UseSparseArrays")
    public SoundPlayer(Context cxt) {
        context = cxt;
        threadMap = new HashMap<Integer, SoundPlayer.PlayThread>();
    }

    private void fillSoundpool(int midiVal, String path) {
        int soundId = soundPool.load(path, 1);
        soundPoolMap.put(midiVal, soundId);

    }

    @SuppressLint("UseSparseArrays")
    public void initSoundpool() {

        soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, srcQuality);
        soundPoolMap = new HashMap<Integer, Integer>();

    }

    @SuppressLint("UseSparseArrays")
    public void setSoundpool(int midiVal, String path) {

        fillSoundpool(midiVal, path);
    }

    public void playSound(NoteName noteName) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float leftVolume = curVolume / maxVolume;
        float rightVolume = curVolume / maxVolume;
        int priority = 1;
        int noLoop = 0;
        float normalPlaybackRate = 1f;
        soundPool.play(soundPoolMap.get(noteName.getMidi()), leftVolume, rightVolume, priority, noLoop,
                normalPlaybackRate);
    }

    public void playNote(int midiValue) {
        PlayThread thread = new PlayThread(midiValue);
        thread.start();
        threadMap.put(midiValue, thread);
    }

    public boolean isNotePlaying(int midiValue) {
        return threadMap.containsKey(midiValue);
    }

    public void stopNote(int midiValue) {

        PlayThread thread = threadMap.get(midiValue);
        if (thread != null) {
            thread.requestStop();
            threadMap.remove(midiValue);
        }

    }

    private class PlayThread extends Thread {
        int midiValue;
        int streamId = 0;

        public PlayThread(int midiValue) {
            this.midiValue = midiValue;
        }

        @Override
        public void run() {

            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float leftVolume = curVolume / maxVolume;
            float rightVolume = curVolume / maxVolume;
            int priority = 1;
            int noLoop = 0;
            float normalPlaybackRate = 1f;
            int a = soundPoolMap.get(midiValue);
            streamId = soundPool.play(soundPoolMap.get(midiValue), leftVolume, rightVolume, priority, noLoop,
                    normalPlaybackRate);

        }

        public synchronized void requestStop() {

            soundPool.stop(streamId);
        }
    }
}

