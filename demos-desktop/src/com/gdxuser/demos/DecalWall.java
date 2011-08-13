package com.gdxuser.demos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.gdxuser.util.Cube;
import com.gdxuser.util.DecalSprite;
import com.gdxuser.util.DemoWrapper;
import com.gdxuser.util.FloorGrid;
import com.gdxuser.util.GuOrthoCam;
import com.gdxuser.util.Log;

public class DecalWall extends DemoWrapper implements InputProcessor {
	private static final int GRID_SIZE = 10;
	float w;
	float h;
	private GuOrthoCam oCam;
	private Cube cube;
	FloorGrid floor;
	private DecalSprite wall;
	private DecalBatch batch;
	private DecalSprite player;

	@Override
	public void create() {
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl10.glDepthFunc(GL10.GL_LESS);

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		oCam = new GuOrthoCam(w, h, GRID_SIZE * 2);
		oCam.position.set(-GRID_SIZE, GRID_SIZE, GRID_SIZE * 2);
		oCam.lookAt(GRID_SIZE / 2, 0, GRID_SIZE / 2);
		// oCam.lookAt(0, 0, 0);

		// put some basic furniture in
		floor = new FloorGrid(GRID_SIZE, GRID_SIZE);
		cube = new Cube().scale(0.5f).pos(0f, 0.5f, 0f);
		wall = new DecalSprite("data/decals/256/3d_side.png");
		wall.sprite.setDimensions(6, 6);
		wall.sprite.setPosition(5, 3, 0);
		float dh = wall.sprite.getHeight();
		float dw = wall.sprite.getWidth();
		Log.out("decal size:" + dh + "," + dw);

		player = new DecalSprite("data/players/full/128/avatar1.png");
		player.sprite.setDimensions(4, 4);
		player.sprite.setPosition(5, 2, 2);
		
		batch = new DecalBatch();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		GL10 gl = Gdx.app.getGraphics().getGL10();
		// gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		oCam.update();
		oCam.apply(gl);
		
		batch.add(wall.sprite);
		batch.flush();
		// oCam.push();
		
		Vector3 ppos = player.sprite.getPosition();
//		oCam.position.set(ppos.x, ppos.y, ppos.z + 10);
//		oCam.lookAt(ppos.x, ppos.y, ppos.z );
		oCam.update();
		oCam.apply(gl);
		batch.add(player.sprite);
		batch.flush();
		
		// oCam.pop();
		oCam.update();
		oCam.apply(gl);
		
		gl.glPushMatrix();
		gl.glColor4f(1, 0, 0, 1f);
		cube.render(gl, GL10.GL_LINE_STRIP);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glColor4f(0, 1, 0, 1f);
		floor.render(gl, GL10.GL_LINE_STRIP);
		gl.glPopMatrix();

	}

}