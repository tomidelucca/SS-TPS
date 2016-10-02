package ss.grupo3.algorithm;

import ss.grupo3.derivative.Derivative;
import ss.grupo3.model.Particle;
import ss.grupo3.oscillation.Oscillation;

public class GearPCAlgorithm implements Algorithm {
	private static double alpha0 = 3.0/16.0;
	private static double alpha1 = 251.0/360.0;
	private static double alpha2 = 1;
	private static double alpha3 = 11.0/18.0;
	private static double alpha4 = 1.0/6.0;
	private static double alpha5 = 1.0/60.0;
	
	private double rp0;
	private double rp1;
	private double rp2;
	private double rp3;
	private double rp4;
	private double rp5;
	
	private double rc0;
	private double rc1;
	private double rc2;
	private double rc3;
	private double rc4;
	private double rc5;
	
	private double R2;
	
	public double r0Predictor(Oscillation osc, double t, double dt) {
//		this.rp0 = Derivative.r0(osc, t) + Derivative.r1(osc, t) * dt
//				+ Derivative.r2(osc, t) * (Math.pow(dt, 2) / 2)
//				+ Derivative.r3(osc, t) * (Math.pow(dt, 3) / 6)
//				+ Derivative.r4(osc, t) * (Math.pow(dt, 4) / 24)
//				+ Derivative.r5(osc, t) * (Math.pow(dt, 5) / 120);
		
		this.rp0 = osc.getX() + osc.getVx() * dt
				+ osc.getAx() * (Math.pow(dt, 2) / 2)
				+ Derivative.r3(osc, t) * (Math.pow(dt, 3) / 6)
				+ Derivative.r4(osc, t) * (Math.pow(dt, 4) / 24)
				+ Derivative.r5(osc, t) * (Math.pow(dt, 5) / 120);
		
		return this.rp0;
	}

	public double r1Predictor(Oscillation osc, double t, double dt) {
//		this.rp1 = Derivative.r1(osc, t) + Derivative.r2(osc, t) * dt
//				+ Derivative.r3(osc, t) * (Math.pow(dt, 2) / 2)
//				+ Derivative.r4(osc, t) * (Math.pow(dt, 3) / 6)
//				+ Derivative.r5(osc, t) * (Math.pow(dt, 4) / 24);

		this.rp1 = osc.getVx() + Derivative.r2(osc, t) * dt
				+ Derivative.r3(osc, t) * (Math.pow(dt, 2) / 2)
				+ Derivative.r4(osc, t) * (Math.pow(dt, 3) / 6)
				+ Derivative.r5(osc, t) * (Math.pow(dt, 4) / 24);
		
		return this.rp1;
	}

	public double r2Predictor(Oscillation osc, double t, double dt) {
//		this.rp2 = Derivative.r2(osc, t) + Derivative.r3(osc, t) * dt
//				+ Derivative.r4(osc, t) * (Math.pow(dt, 2) / 2)
//				+ Derivative.r5(osc, t) * (Math.pow(dt, 3) / 6);
		
		this.rp2 = osc.getAx() + Derivative.r3(osc, t) * dt
				+ Derivative.r4(osc, t) * (Math.pow(dt, 2) / 2)
				+ Derivative.r5(osc, t) * (Math.pow(dt, 3) / 6);
		
		return this.rp2;
	}

	public double r3Predictor(Oscillation osc, double t, double dt) {
		this.rp3 = Derivative.r3(osc, t) + Derivative.r4(osc, t) * dt
				+ Derivative.r5(osc, t) * (Math.pow(dt, 2) / 2);
		
		return this.rp3;
	}

	public double r4Predictor(Oscillation osc, double t, double dt) {
		this.rp4 = Derivative.r4(osc, t) + Derivative.r5(osc, t) * dt;
		
		return this.rp4;
	}

	public double r5Predictor(Oscillation osc, double t, double dt) {
		this.rp5 = Derivative.r5(osc, t);
		
		return this.rp5;
	}

	public double R2(Oscillation osc, double t, double dt) {
		double deltaA = Derivative.r2(osc, t + dt) - this.rp2;
		this.R2 = (deltaA*Math.pow(dt, 2))/2;
		
		return this.R2;
	}
	
	public double r0Corrector(Oscillation osc, double t, double dt) {
		this.rc0 = this.rp0 + alpha0*this.R2*(1/Math.pow(dt, 0));
		
		return this.rc0;
	}

	public double r1Corrector(Oscillation osc, double t, double dt) {
		this.rc1 = this.rp1 + alpha1*this.R2*(1/Math.pow(dt, 1));
		
		return this.rc1;
	}
	
	public double r2Corrector(Oscillation osc, double t, double dt) {
		this.rc2 = this.rp2 + alpha2*this.R2*(2/Math.pow(dt, 2));
		
		return this.rc2;
	}
	
	public double r3Corrector(Oscillation osc, double t, double dt) {
		this.rc3 = this.rp3 + alpha3*this.R2*(6/Math.pow(dt, 3));
		
		return this.rc3;
	}
	
	public double r4Corrector(Oscillation osc, double t, double dt) {
		this.rc4 = this.rp4 + alpha4*this.R2*(24/Math.pow(dt, 4));
		
		return this.rc4;
	}
	
	public double r5Corrector(Oscillation osc, double t, double dt) {
		this.rc5 = this.rp5 + alpha5*this.R2*(120/Math.pow(dt, 5));
		
		return this.rc5;
	}

	public void run(Oscillation osc, double t, double dt) {
		r0Predictor(osc, t, dt);
		r1Predictor(osc, t, dt);
		r2Predictor(osc, t, dt);
		r3Predictor(osc, t, dt);
		r4Predictor(osc, t, dt);
		r5Predictor(osc, t, dt);
		
		R2(osc, t, dt);
		
		r0Corrector(osc, t, dt);
		r1Corrector(osc, t, dt);
		r2Corrector(osc, t, dt);
		r3Corrector(osc, t, dt);
		r4Corrector(osc, t, dt);
		r5Corrector(osc, t, dt);
		
		osc.setX(this.rc0);
		osc.setVx(this.rc1);
		osc.setAx(this.rc2);
	}
	
	@Override
	public double positionX(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double positionY(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double velocityX(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double velocityY(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

}
