package body;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.me.maee.MAE;
import com.me.maee.Stats;
import com.me.maee.Utils;
import com.me.maee.Vec;

public class Shape extends Body {

	public ArrayList<Vec> points = new ArrayList<Vec>();
	private ShapeRenderer CircleRenderer;
	
	public Shape(ArrayList<Vec> points, Vec pos) {
		
		this.points = points;
		this.pos = new Vec(pos.x,pos.y);
		setRenderer(); 
		setVelocity(new Vec());;
		defineCircle();
		aabb = buildAABB();
		type = BodyType.SHAPE;
		
	}
public Shape(ArrayList<Vec> points) {
		
		this.points = points;
		setRenderer(); 
		setVelocity(new Vec());;
		defineCircle();
		aabb = buildAABB();
		type = BodyType.SHAPE;
		
	}
public Shape (){
	generate();
	}

	private void defineCircle() {
		//����������� ������������ �����
	}

	int n;
	
	public boolean isOriented(ArrayList<Vec> p){
		return false;
	}

	@Override
	public void draw() {
		//System.out.println("=====");
		aabb.draw();
		renderer.begin(ShapeType.Line);
		Vec p1;
		Vec p2;
		for (int i = 0 ; i < points.size()-1 ; i++ ){
			p1 = points.get(i);
			p2 = points.get(i+1);
			//System.out.println("P1: "+p1.x+" "+p1.y+" ; P2:"+p2.x+" "+p2.y );
			renderer.line((float)p1.x, (float)p1.y, (float)p2.x, (float)p2.y);
		}
		p1 = points.get(points.size()-1);
		p2 = points.get(0);
		renderer.line((float)p1.x,(float) p1.y,(float)  p2.x,(float)  p2.y);
		renderer.end();
		CircleRenderer.begin(ShapeType.Circle);
		CircleRenderer.circle(pos.x, pos.y, R);
		CircleRenderer.end();
		posRenderer.begin(ShapeType.Point);
		posRenderer.setColor(0, 1, 0, 1);;
		posRenderer.point(pos.x, pos.y, 0);
		posRenderer.end();
	}
	
	@Override
	public void update(float deltaTime) {
		//pos.write();
		
		updateRotation(deltaTime);
		updatePosition(deltaTime);
		setColor();
		aabb = buildAABB();
	}
	
	private void updateRotation(float dTime){
		
	}
	
	private void updatePosition(float dTime){
		pos.add(vel);
		for (Vec i : points){
			i.add(vel);
		}
	}

	@Override
	public float defineSquare() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	public void rotate(float angle){
		
		for (int i = 0 ; i < points.size() ; i++ ){
			Vec newDot = Utils.rotatePoint(angle, points.get(i) ,pos);
			points.get(i).x = newDot.x;
			points.get(i).y = newDot.y;
		}
		
		/*for (Vec i : points){
			System.out.println("=======");
			System.out.println(""+ i.x+ " "+ i.y);
			i = Utils.rotatePoint(angle, i ,pos);
			System.out.println(""+ i.x+ " "+ i.y);
		}*/
	}

	@Override
	public AABB buildAABB() {
		//! �������� ���������������� �����, ������ ���� �������� �������, ���� ��������� �� ��
		//System.out.println(points);
		Vec a = points.get(0);
		float left = a.x;
		float right = a.x;
		float up = a.y;
		float down = a.y;
		for (Vec i: points){
			if (left > i.x) left = i.x;
			if (right < i.x) right = i.x;
			if (up < i.y) up = i.y;
			if (down > i.y) down = i.y;
		}
		return new AABB(new Vec (left,down), new Vec (right,up));
	}
	public void generate(){
		System.out.println("Generating new shape");
		Random rand = new Random();
		
		float vx,vy;
		if (rand.nextInt(2) == 1) vx = rand.nextFloat() * MAE.MAX_VELOCITY;
		else vx = - rand.nextFloat() * MAE.MAX_VELOCITY;
		if (rand.nextInt(2) == 1) vy = rand.nextFloat() * MAE.MAX_VELOCITY;
		else vy = - rand.nextFloat() * MAE.MAX_VELOCITY;
		this.vel = new Vec(vx,vy);
		
		
		float square = 0;
		Vec pos = new Vec (rand.nextFloat()*MAE.GLOBAL_WIDTH,rand.nextFloat()*MAE.GLOBAL_HEIGHT);
		R = rand.nextFloat() * (MAE.MAX_RADIUS-MAE.MIN_RADIUS)+MAE.MIN_RADIUS;
		float angle = (float) (rand.nextFloat()*Math.PI*R*2);
		int amount = rand.nextInt(MAE.MAX_DOTS-2) + 4 ;
		System.out.println(amount);
		ArrayList<Vec> dots = new ArrayList<Vec>();
		////
		//���������� ������� ��������
		dots.add(new Vec(pos.x-R,pos.y)); // ������ �����, ����������� ������� �� ����� ��������
		
		int N = rand.nextInt(amount - 2);
		System.out.println(" "+amount+" = "+N+" + "+(amount-N-2));
		
		float curH = 0;
		float prevH = 0;
		float[] K = splitLine(N,R+R);
		for (int i = 0; i < N; i++){
			//System.out.println(" "+K[i]);
			//D = D - D*a;
			float maxH =(float)Math.sin( (Math.acos( K[i]/R-1 )))*R;
			//System.out.println(maxH);
			curH = rand.nextFloat()*maxH;
			
			dots.add(new Vec (pos.x+(K[i]-R),pos.y+curH ));
			//System.out.println("square:"+square);
			if (i != 0)
				square = square +  ((prevH+curH)*(K[i]-K[i-1])); // ������� ����� ��������
			else square = square + ((prevH+curH)*(K[0])*R); 
			prevH = curH;
		}
		////
		//������ ������ ��������
		dots.add(new Vec(pos.x+R,pos.y));// ������ �����, ������� �� ������ ����� ��������
		square = square; //TODO: ��� ����� ������ ������� ���������� ������������
		K = splitLine(amount - N -2,R+R);
		for (int i = 0; i <amount - N-2; i++){
			//System.out.println(" "+K[i]);
			//D = D - D*a;
			float maxH =(float)Math.sin( (Math.acos( K[i]/R-1 )))*R;
			//System.out.println(maxH);
			curH = rand.nextFloat()*maxH;
			
			dots.add(new Vec (pos.x-(K[i]-R),pos.y-curH ));
		
			if (i != 0)
				square = square +  ((prevH+curH)*(K[i]-K[i-1])); // ������� ����� ��������
			else square = square + ((prevH+curH)*(K[0])*R); 
			prevH = curH;
			//System.out.println("square:"+square);
		}
		////
		this.pos = pos;
		this.angle = Utils.getRadiusVector(angle);
		rotate(angle);
		this.points = dots;
		aabb = buildAABB();
		type = BodyType.SHAPE;
		setRenderer();
		posRenderer = new ShapeRenderer();
		//System.out.println("square:"+square);
		mass = square;
		//System.out.println("Final square:"+square);
		CircleRenderer= new ShapeRenderer();
		CircleRenderer.setColor(0.3f, 0.3f, 0.3f, 1);
	}
	
	private static float[] splitLine (int N,float L){
		//��������� ������� �� N ��������� ������
		Random rand = new Random();
		float[] M = new float[N];
		float[] K = new float[N];
		float s = 0;
		for (int i = 0; i < N; i++) {
			M[i] = rand.nextFloat();
			s += M[i]; 
		}
		float a = 0;
		for (int i = 0; i < N; i++) {
			//K[i] = M[i]/s*L; // ������������ ����� ��������
			a = a + M[i]/s*L;
			K[i] = a; // ��� ����������� ��������� ���� ����� ������������ "�������" 
		}
		return K;
	}
}
