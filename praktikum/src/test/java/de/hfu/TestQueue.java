package de.hfu;


import org.junit.Assert;
import org.junit.Test;

public class TestQueue {

	@Test
	public void testNormalArrayBehaviour() {
		Queue queue = new Queue(3);
		for(int i = 0; i < 3; i++) {
			queue.enqueue(i);
		}
		Assert.assertEquals(0, queue.dequeue());
		Assert.assertEquals(1, queue.dequeue());
		Assert.assertEquals(2, queue.dequeue());
	}
	@Test
	public void testCycledLoopBehaviour() {
		Queue queue = new Queue(3);
		for(int i = 0; i < 6; i++) {
			queue.enqueue(i);
		}
		Assert.assertEquals(0, queue.dequeue());
		Assert.assertEquals(1, queue.dequeue());
		Assert.assertEquals(5, queue.dequeue());
		
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor() {
		@SuppressWarnings("unused")
		Queue queue = new Queue(-1);
	}
	@Test(expected=IllegalStateException.class)
	public void testDequeueException() {
		Queue queue = new Queue(3);
		for(int i = 0; i < 6; i++) {
			queue.enqueue(i);
		}
		for(int i = 0; i < 7; i++) {
			queue.dequeue();
		}
	}

}
