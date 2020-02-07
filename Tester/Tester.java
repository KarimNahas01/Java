import org.junit.Test;
import org.junit.Before;

import java.awt.*;

import static junit.framework.TestCase.*;

public class Tester{
    Saab95 saab = new Saab95(0,0);
    Volvo240 volvo = new Volvo240(0,0);
    Scania scania = new Scania(Color.black, 500, "Scania");
    Truck truck = new Truck(Color.white, "Truck", 0,0, 30);


    @Before
    public void init() {
        saab.setCurrentSpeed(30);
        saab.setCurrentDir(Vehicle.direction.NORTH);
        volvo.setCurrentSpeed(30);
        volvo.setCurrentDir(Vehicle.direction.NORTH);
    }

    @Test
    public void saab95() {
        assertEquals(2, saab.getNrDoors());
        assertEquals(125.0, saab.getEnginePower());
        assertEquals(Color.red, saab.getColor());
        assertEquals("Saab95", saab.modelName);
    }

    @Test
    public void volvo240() {
        assertEquals(4, volvo.getNrDoors());
        assertEquals(100.0, volvo.getEnginePower());
        assertEquals(Color.black, volvo.getColor());
        assertEquals("Volvo240", volvo.modelName);

    }

    @Test
    public void startEngine() {
        saab.startEngine();
        volvo.startEngine();
        assertEquals(0.1, saab.getCurrentSpeed());
        assertEquals(0.1, volvo.getCurrentSpeed());

        saab.stopEngine();
        volvo.stopEngine();

        saab.setCurrentSpeed(30);
        volvo.setCurrentSpeed(30);
    }

    @Test
    public void turboTester() {
        saab.setTurboOn();

        saab.incrementSpeed(20);
        assertEquals(62.5, saab.getCurrentSpeed());

        saab.decrementSpeed(20);
        assertEquals(30.0, saab.getCurrentSpeed());

        saab.setTurboOff();
        saab.incrementSpeed(20);
        assertEquals(55.0, saab.getCurrentSpeed());

        saab.decrementSpeed(20);
        assertEquals(30.0, saab.getCurrentSpeed());

    }

    @Test
    public void colorChange() {
        saab.setColor(Color.BLACK);
        assertEquals(Color.BLACK, saab.getColor());

        volvo.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, volvo.getColor());




    }

    @Test
    public void incrementAndDecrementSpeed() {
        saab.incrementSpeed(50.0);
        volvo.incrementSpeed(30.0);
        assertEquals(92.5, saab.getCurrentSpeed());
        assertEquals(67.5, volvo.getCurrentSpeed());

        saab.decrementSpeed(50.0);
        volvo.decrementSpeed(30.0);
        assertEquals(30.0, saab.getCurrentSpeed());
        assertEquals(30.0, volvo.getCurrentSpeed());
    }

    @Test
    public void testMove() {

        double tempY = saab.getY();
        double tempX = saab.getX();

        saab.move();
        assertTrue(saab.getY() < tempY);

        saab.setCurrentDir(Vehicle.direction.EAST);

        saab.move();
        assertTrue(saab.getX() > tempX);

    }

    @Test
    public void testTurnLeft() {
        saab.turnLeft();
        assertSame(saab.getCurrentDir(), Vehicle.direction.WEST);

        saab.turnLeft();
        assertSame(saab.getCurrentDir(), Vehicle.direction.SOUTH);

        saab.turnLeft();
        assertSame(saab.getCurrentDir(), Vehicle.direction.EAST);

        saab.turnLeft();
        assertSame(saab.getCurrentDir(), Vehicle.direction.NORTH);

        volvo.turnLeft();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.WEST);

        volvo.turnLeft();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.SOUTH);

        volvo.turnLeft();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.EAST);

        volvo.turnLeft();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.NORTH);
    }

    @Test
    public void testTurnRight() {
        saab.turnRight();
        assertSame(saab.getCurrentDir(), Vehicle.direction.EAST);

        saab.turnRight();
        assertSame(saab.getCurrentDir(), Vehicle.direction.SOUTH);

        saab.turnRight();
        assertSame(saab.getCurrentDir(), Vehicle.direction.WEST);

        saab.turnRight();
        assertSame(saab.getCurrentDir(), Vehicle.direction.NORTH);

        volvo.turnRight();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.EAST);

        volvo.turnRight();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.SOUTH);

        volvo.turnRight();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.WEST);

        volvo.turnRight();
        assertSame(volvo.getCurrentDir(), Vehicle.direction.NORTH);
    }

    @Test
    public void speedFactor() {
        assertEquals(1.25, saab.speedFactor()); // Check when turbo is on too...

        assertEquals(1.25, volvo.speedFactor());

    }

    @Test
    public void gasAndBrake() {
        double tmpSaab = saab.getCurrentSpeed();
        double tmpVolvo = volvo.getCurrentSpeed();

        saab.gas(0.5);
        assertEquals(saab.getCurrentSpeed(), tmpSaab + Math.min(saab.speedFactor() * 0.5, saab.enginePower));

        saab.brake(0.5);
        assertEquals(saab.getCurrentSpeed(), tmpSaab);

        volvo.gas(0.3);
        assertEquals(volvo.getCurrentSpeed(), tmpVolvo + volvo.speedFactor() * 0.3);

        volvo.brake(0.3);
        assertEquals(volvo.getCurrentSpeed(), tmpVolvo);


    }

    @Test
    public void raiseAndLower() {
        scania.raise(30.0);
        assertEquals(scania.getFlatBedAngle(), 30.0);
        scania.raise(50.0);
        assertEquals(scania.getFlatBedAngle(), 30.0);

        scania.lower(30.0);
        assertEquals(scania.getFlatBedAngle(), 0.0);
        scania.lower(10.0);
        assertEquals(scania.getFlatBedAngle(), 0.0);

        scania.setCurrentSpeed(50);
        scania.raise(30);
        assertEquals(scania.getFlatBedAngle(), 0.0);
        scania.lower(30);
        assertEquals(scania.getFlatBedAngle(), 0.0);
        scania.setCurrentSpeed(0);
    }

    @Test
    public void moveScania() {
        scania.setCurrentSpeed(30);

        double tempY = scania.getY();

        scania.move();
        assertEquals(scania.getY(), tempY-30);

        scania.setCurrentSpeed(0);
        scania.raise(30);
        scania.setCurrentSpeed(30);
        scania.move();
        assertEquals(scania.getY(), tempY-30);

        scania.setCurrentSpeed(0);
    }

    @Test
    public void loadAndUnloadTruck() throws Exception {
        for (int i = 0; i < 8; i++) {
            truck.load(volvo);
        }
        assertEquals(truck.stack.size(),8);
        boolean overLoad = false;
        try {
            truck.load(volvo);
        } catch (Exception e) {
            overLoad = true;
        }
        assertTrue(overLoad);

        for (int i = 0; i < 8; i++) {
            truck.unload();
        }
        assertEquals(truck.stack.size(), 0);

        boolean tooLarge = false;
        try {
            truck.load(truck);
        } catch (Exception e) {
            tooLarge = true;
        }
        assertTrue(tooLarge);
        assertEquals(truck.stack.size(), 0);
    }

    public void lowerAndRaiseFlatBed() {
        truck.setCurrentSpeed(30);
        truck.raise();
        assertFalse(truck.flatBedUp);

        truck.raise();
        assertTrue(truck.flatBedUp);

        truck.lower();
        assertFalse(truck.flatBedUp);
    }

    public void loadCoordinates() throws Exception {
        for (int i = 0; i < 8; i++) {
            truck.load(volvo);
        }
        for (Vehicle car: truck.stack) {
            assertEquals(car.getX(), truck.getX());
            assertEquals(car.getY(), truck.getY());
        }
    }
}
