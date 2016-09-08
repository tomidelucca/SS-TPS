package ss.grupo3.model;

import java.util.Comparator;
import java.util.PriorityQueue;

import ss.grupo3.event.CollisionParticleEvent;
import ss.grupo3.event.CollisionWallEvent;
import ss.grupo3.ovito.OvitoFile;

public class BrownianMotion {
	private PriorityQueue<Event> eventQueue;
	private Particle[] particles;
	private double L;
	private double dt;
	//tengo en cuenta los primeros 10 eventos
	int eventCount = 10;
	
	public BrownianMotion(Particle[] particles, double L) {
		this.eventQueue = new PriorityQueue<Event>(new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return (o1.getTime() < o2.getTime())? -1 : 1;
			}
		});
		
		this.particles = particles;
		this.dt = 0;
		this.L = L;
	}
	
//	public void printParticules() {
//		for(int i = 0; i < particles.length; i++)
//			System.out.println(particles[i]);
//	}
	
	public void run(int iteration) {
		OvitoFile of = new OvitoFile("output/test1.xyz");
		int i = 0;
		Event event = null;
		
		try {
			calculateEventsQueue(particles);
			
			while(i < iteration && !this.eventQueue.isEmpty()) {
//				System.out.println("ITERACION: " + i);
//				printParticules();
				of.write(i, L, particles);
				event = executeFirstEvent();
				updateQueue(event, i);
				i++;
			}

			of.closeFile();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateQueue(Event event, int it) throws CloneNotSupportedException {
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
				if(anEvent instanceof CollisionWall)
					anEvent = CollisionWallEvent.check(anEvent.getA(), L);
				if(anEvent instanceof CollisionParticle)
					anEvent = CollisionParticleEvent.check(anEvent.getA(), anEvent.getB());

				if(anEvent != null) {
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
	
	private Event executeFirstEvent() throws CloneNotSupportedException {
		Event firstEvent = eventQueue.poll();

		updateParticlePosition(firstEvent.getTime());
		firstEvent.update();
		this.dt = firstEvent.getTime();

		return firstEvent;
	}

	private void calculateEventsQueue(Particle[] particle) throws CloneNotSupportedException {
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
}
