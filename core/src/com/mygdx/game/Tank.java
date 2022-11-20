package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Tank {
    Body body;
    BodyDef bodyDef;
    Texture texture;
    TextureRegion textRegion;
    float length;
    float breadth;
    float density;
    Tank(float x, float y, int type, boolean flip) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        if (type >= 1 && type <= 5) texture = new Texture("TANKS/tank" + type + ".png");
        else texture = new Texture("TANKS/tank2.png");
        textRegion = new TextureRegion(texture);
        textRegion.flip(flip, false);
        length = 81;
        breadth = 44;
        density = 1f;
    }
    Tank(float x, float y, boolean flip) {
        this(x, y, 3, flip);
    }
    public void render(World world) {
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length / 2,breadth / 2);
        body.createFixture(shape, density);
//        body.setGravityScale(0.5f);
        shape.dispose();
    }
    public void move() {
        body.setGravityScale(10);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            body.applyLinearImpulse(new Vector2(-100000 * 5, 0), new Vector2(body.getPosition().x, body.getPosition().y), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            body.applyLinearImpulse(new Vector2(1000000 * 5, 0), new Vector2(body.getPosition().x, body.getPosition().y), true);
//            body.applyForceToCenter(1000, 0, false);
//            body.setLinearVelocity(50, 0);
        }
    }
    public void update(SpriteBatch batch) {
        batch.draw(textRegion, body.getPosition().x - length / 2, body.getPosition().y - breadth / 2,length / 2, breadth / 2, length, breadth, 1, 1, (float) Math.toDegrees(body.getAngle()));
    }
}
