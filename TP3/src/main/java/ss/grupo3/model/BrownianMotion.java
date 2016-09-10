package ss.grupo3.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import ss.group3.generator.CollisionPerDtFileGenerator;
import ss.grupo3.event.CollisionParticleEvent;
import ss.grupo3.event.CollisionWallEvent;
import ss.grupo3.ovito.OvitoFile;

public class BrownianMotion {
	private PriorityQueue<Event> eventQueue;
	private Particle[] particles;
	private List<Integer> eventsPerDt;
	private double L;
	private double total_time;
	private double dt;
	private double epsilon = 0.001;
	
	public BrownianMotion(Particle[] particles, double L, double dt, double total_time) {
		this.eventsPerDt = new ArrayList<Integer>();
		this.eventQueue = new PriorityQueue<Event>(new Comparator<Event>() {
			@Override
			public int compare(Event o1, Event o2) {
				return (o1.getTime() < o2.getTime())? -1 : 1;
			}
		});
		
		this.particles = particles;
		this.L = L;
		this.dt = dt;
		this.total_time = total_time;
	}
	
	public void run() {
		OvitoFile of = new OvitoFile("output/test1.xyz");
		Event event = null;
		double auxtime = 0;
		double eventTime = -1;
		double sum_time = 0;
		int dtCollision = 0;
		
		try {
			//genero una cola de eventos inicial
			predict(particles);
			//guardo el estado inicial de todas las particulas
			of.write(L, particles);			
			//TODO cambiar lo de iteration por un dt para guardar los estados
			while(this.total_time >= epsilon) {

				if(eventTime != -1) {
					if(sum_time + eventTime < this.dt) {
						sum_time += eventTime;
						updateParticlePosition(event.getTime());
						event.execute();
						updateQueue(event);
						dtCollision++;
						this.total_time -= eventTime;
						eventTime = -1;
					} else if (sum_time + eventTime > this.dt) {
							auxtime = this.dt - sum_time;
							eventTime -= auxtime;
							this.total_time -= auxtime;
							of.write(L, particles, auxtime);
							eventsPerDt.add(dtCollision);
							dtCollision = 0;
							
							while(eventTime > this.dt && this.total_time > 0) {
								auxtime += this.dt;
								eventTime -= this.dt;
								this.total_time -= this.dt;
								of.write(L, particles, auxtime);
								eventsPerDt.add(dtCollision);
								dtCollision = 0;
							}
							
							if(this.total_time > 0) {
								sum_time = eventTime;
								updateParticlePosition(event.getTime());
								event.execute();
								updateQueue(event);
								dtCollision++;
								this.total_time -= sum_time;
								eventTime = -1;								
							}
					
					} 				
				} else {
					event = getFirstEvent();	
					if(event != null)
						eventTime = event.getTime();						

				}
				
//				System.out.println("event time: " + event.getTime());
//				System.out.println("total time: " + this.total_time);
			}

			of.closeFile();
			
			if(dtCollision != 0)
				eventsPerDt.add(dtCollision);

			//Guardo la cantidad de colisiones por cada dt.
			CollisionPerDtFileGenerator.write("CPDT/result.txt", eventsPerDt, this.dt);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Guardo en archivo la cantidad de eventos que suceden cada dt
		CollisionPerDtFileGenerator.write("CPDT/output.txt", this.eventsPerDt, this.dt);
	}
	
	private void updateQueue(Event event) throws CloneNotSupportedException {
		Event anEvent = null;
		Particle a = event.getA();
		Particle b = event.getB();
		int length = eventQueue.size();
		PriorityQueue<Event> auxEventQueue = new PriorityQueue<Event>(new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return (o1.getTime() < o2.getTime())? -1 : 1;
			}
		});
		
		for(int i = 0; i < length; i++) {
			anEvent = eventQueue.poll();

			if(!anEvent.contains(a,b)) {
				if(anEvent.isValid()) {
					anEvent.setTime(anEvent.getTime() - event.getTime());					
					auxEventQueue.add(anEvent);
				}
			}
		}
		
		eventQueue.addAll(auxEventQueue);
		calculatParticleEvent(a);
		calculatParticleEvent(b);		
	}
	
	private void updateParticlePosition(double t) {
		for(Particle p: particles) {
			p.updatePosition(t);
		}
	}
	
	private void predict(Particle[] particle) throws CloneNotSupportedException {
		Event e;
		
		for(int i = 0; i < particle.length; i++) {
			e = CollisionWallEvent.check(particle[i], L);
			if(e != null)
				eventQueue.add(e);
		}
				
		for(int i = 0; i < particle.length; i++)
			for(int j = i + 1; j < particle.length; j++) {
				e = CollisionParticleEvent.check(particle[i], particle[j]);
				if(e != null)
					eventQueue.add(e);
			}
	}	
	
	private void calculatParticleEvent(Particle p) throws CloneNotSupportedException {
		Event e;
		
		if(p == null)
			return; 

		e = CollisionWallEvent.check(p, L);
		if(e != null)
			eventQueue.add(e);

		for(int i = 0; i < particles.length; i++) {
			if(!p.equals(particles[i])) {
				e = CollisionParticleEvent.check(p, particles[i]);
				if(e != null)
					eventQueue.add(e);				
			}
		}
	}	
	
	private Event getFirstEvent() {
		Event e = eventQueue.poll();
		
		while(e != null && !e.isValid())
			e = eventQueue.poll();
		
		return e;
	}
}
