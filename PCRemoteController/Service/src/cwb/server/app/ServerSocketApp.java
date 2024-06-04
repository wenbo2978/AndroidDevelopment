package cwb.server.app;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cwb.server.socket.CmdServerSocket;


public class ServerSocketApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
			new CmdServerSocket().work();
			
			
	}

}
