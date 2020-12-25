/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Track {
	
	private String filename;
	private Clip clip;
	private FloatControl volume;
	private FloatControl balance;
	private FloatControl pan;
	private BooleanControl mute;
	
	public Track(String filename) {
		
		this.filename = filename;

		try {
			clip = AudioSystem.getClip();
			AudioInputStream in = AudioSystem.getAudioInputStream(Track.class.getClassLoader().getResource(filename));
			clip.open(in);
			
			this.balance = (FloatControl) clip.getControl(FloatControl.Type.BALANCE);
			this.volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			this.pan = (FloatControl) clip.getControl(FloatControl.Type.PAN);
			this.mute = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
	}

	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void loop(int n) {
		clip.loop(n);
	}
	
	public void stop() {
		clip.stop();
		reset();
	}
	
	public void reset() {
		clip.setMicrosecondPosition(0);
	}
	
	public void setMute(boolean b) {
		mute.setValue(b);
	}
	
	public void setVolume(float v) {
		volume.setValue(v);
		long stopTime = System.currentTimeMillis();
	}
	
	public void sleep(double millis) {
		try {
			Thread.sleep((long) millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
