package painter;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Parent class for all shapes
abstract class Shape implements Serializable {

    protected int x1, y1, x2, y2;
    protected Color color;
    protected boolean isFilled;
    protected boolean isDotted;

    // Constructor
    public Shape(int x1, int y1, int x2, int y2, Color color, boolean isFilled, boolean isDotted) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.isFilled = isFilled;
        this.isDotted = isDotted;
    }

    // Abstract method to draw the shape ***** NEED TO OVERRIDE *****
    public abstract void draw(Graphics2D g);

    public void fill(Graphics2D g) {
    }

    // Abstract method to draw the shape ***** NEED TO OVERRIDE *****
    public abstract void drawDotted(Graphics2D g);
}

// Line class
class Line extends Shape {

    public Line(int x1, int y1, int x2, int y2, Color color, boolean isFilled, boolean isDotted) {
        super(x1, y1, x2, y2, color, isFilled, isDotted);
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(1));
        g.drawLine(x1, y1, x2, y2);
    }

    public void drawDotted(Graphics2D g) {
        g.setColor(color);
        float[] dashPattern = {10, 5}; // Dash pattern: 10 pixels on, 5 pixels off
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        g.drawLine(x1, y1, x2, y2);
    }
}

// Rectangle class
class Rectangle extends Shape {

    public Rectangle(int x1, int y1, int x2, int y2, Color color, boolean isFilled, boolean isDotted) {
        super(x1, y1, x2, y2, color, isFilled, isDotted);

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(1));
        g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void fill(Graphics2D g) {
        g.setColor(color);
        g.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void drawDotted(Graphics2D g) {
        g.setColor(color);
        float[] dashPattern = {10, 5}; // Dash pattern: 10 pixels on, 5 pixels off
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
}

// Oval class
class Oval extends Shape {

    public Oval(int x1, int y1, int x2, int y2, Color color, boolean isFilled, boolean isDotted) {
        super(x1, y1, x2, y2, color, isFilled, isDotted);

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(1));
        g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void fill(Graphics2D g) {
        g.setColor(color);
        g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void drawDotted(Graphics2D g) {
        g.setColor(color);
        float[] dashPattern = {10, 5}; // Dash pattern: 10 pixels on, 5 pixels off
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
}

public class Painter extends Applet {

    private ArrayList<Shape> shapes = new ArrayList<>(); // to store all shapes
    private Color currentColor = Color.RED; // Default color
    private String currentShape = "Line"; // Default shape
    Shape shape = null;
    private Checkbox fillCheckbox, dottedCheckbox;
    private boolean isDrawing = false;
    private boolean isFilled, isDotted = false;
    private int currx1, curry1, currx2, curry2; // current line coordinates
    private int shapeCount = 0;
    private int prevx, prevy;

    // Buttons
    private Button redButton, blueButton, greenButton;
    private Button lineButton, rectButton, ovalButton;
    private Button freeHandButton, eraserButton, clearButton, undoButton;
    private Button saveButton, openButton;

    @Override
    public void init() {
        // Creating buttons
        redButton = new Button("Red");
        blueButton = new Button("Blue");
        greenButton = new Button("Green");

        lineButton = new Button("Line");
        rectButton = new Button("Rectangle");
        ovalButton = new Button("Oval");

        eraserButton = new Button("Eraser");
        freeHandButton = new Button("Free Hand");
        undoButton = new Button("Undo");
        clearButton = new Button("Clear All");
        saveButton = new Button("Save");
        openButton = new Button("Open");

        fillCheckbox = new Checkbox("Fill");
        dottedCheckbox = new Checkbox("Dotted");

        add(redButton);
        add(blueButton);
        add(greenButton);
        add(lineButton);
        add(rectButton);
        add(ovalButton);
        add(freeHandButton);
        add(undoButton);
        add(eraserButton);
        add(clearButton);
        add(fillCheckbox);
        add(dottedCheckbox);
        add(saveButton);
        add(openButton);

        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.RED;
                repaint();
            }
        });
        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.BLUE;
                repaint();
            }
        });
        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.GREEN;
                repaint();
            }
        });
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Line";
                repaint();
            }
        });
        rectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Rectangle";
                repaint();
            }
        });
        ovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Oval";
                repaint();
            }
        });
        freeHandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Free Hand";
                repaint();
            }
        });
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (shapeCount > 0) {
                    shapes.remove(shapeCount - 1);
                    shapeCount--;
                }
                repaint();
            }
        });
        eraserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Eraser";
                currentColor = Color.WHITE;
                repaint();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapes.clear();
                shapeCount = 0;
                currentShape = "Line";
                currentColor = Color.RED;
                isDrawing = false;
                repaint();
            }
        });

        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDrawing();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDrawing();
            }
        });
        fillCheckbox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                // Check if the event source is the fill checkbox
                if (e.getSource() == fillCheckbox) {
                    isFilled = fillCheckbox.getState();
                }
            }
        });
        dottedCheckbox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                // Check if the event source is the dotted checkbox
                if (e.getSource() == dottedCheckbox) {
                    isDotted = dottedCheckbox.getState();
                }
            }
        });

        // Mouse events
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                currx1 = e.getX();
                curry1 = e.getY();
                currx2 = e.getX();
                curry2 = e.getY();
                prevx = currx1; // previous position
                prevy = curry1;
                isDrawing = true;

                repaint();

            }

            public void mouseReleased(MouseEvent e) {
                if (isDrawing) {
                    currx2 = e.getX();
                    curry2 = e.getY();
                    if (!currentShape.equals("Eraser") && !currentShape.equals("Free Hand")) {
                        switch (currentShape) {
                            case "Line":
                                shape = new Line(currx1, curry1, currx2, curry2, currentColor, false, isDotted);
                                break;
                            case "Rectangle":
                                shape = new Rectangle(currx1, curry1, currx2, curry2, currentColor, isFilled, isDotted);
                                break;
                            case "Oval":
                                shape = new Oval(currx1, curry1, currx2, curry2, currentColor, isFilled, isDotted);
                                break;

                        }
                        if (shape != null) {
                            shapes.add(shapeCount, shape); // Adding shape to the list
                            shapeCount++;
                        }
                    }
                    isDrawing = false;
                    repaint();

                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    currx2 = e.getX();
                    curry2 = e.getY();
                    if (currentShape.equals("Eraser")) {
                        shapes.add(shapeCount, new Rectangle(currx2 - 5, curry2 - 5, currx2 + 5, curry2 + 5, Color.WHITE, true, false));
                        shapeCount++;
                    } else if (currentShape.equals("Free Hand")) {
                        shapes.add(new Line(prevx, prevy, currx2, curry2, currentColor, false, false));
                        shapeCount++;
                        prevx = currx2;
                        prevy = curry2;
                    }
                    repaint();
                }

            }
        });

    }

    private void openDrawing() {
        Frame tempFrame = new Frame();
        tempFrame.setVisible(false);
        FileDialog fileDialog = new FileDialog(tempFrame, "Open Drawing", FileDialog.LOAD);
        fileDialog.setFile("*.jpg");
        fileDialog.setVisible(true);

        String filePath = fileDialog.getDirectory() + fileDialog.getFile();
        if (filePath != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                shapes = (ArrayList<Shape>) ois.readObject(); //casting the output to an arraylist of type shape
                shapeCount = shapes.size();
                repaint();
                System.out.println("Drawing loaded from " + filePath);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error loading file: " + ex.getMessage());
            }
        }
    }

    private void saveDrawing() {
        Frame tempFrame = new Frame();
        tempFrame.setVisible(false);
        FileDialog fileDialog = new FileDialog(tempFrame, "Save Drawing", FileDialog.SAVE);
        fileDialog.setFile("*.jpg");
        fileDialog.setVisible(true);

        String filePath = fileDialog.getDirectory() + fileDialog.getFile();
        tempFrame.dispose(); // getting rid of temporary frame

        if (filePath != null && fileDialog.getFile() != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(shapes); 
                System.out.println("Drawing saved to " + filePath);
            } catch (IOException ex) {
                System.out.println("Error saving file: " + ex.getMessage());
            }
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Draw all shapes in the list

        for (Shape prev_shape : shapes) {
            if (prev_shape.isFilled) {
                prev_shape.fill(g2d);
            } else if (prev_shape.isDotted) {
                prev_shape.drawDotted(g2d);
            } else {
                prev_shape.draw(g2d);
            }
        }
        if (isDrawing && shape != null) {

            switch (currentShape) {
                case "Line":
                    shape = new Line(currx1, curry1, currx2, curry2, currentColor, false, isDotted);
                    break;
                case "Rectangle":
                    shape = new Rectangle(currx1, curry1, currx2, curry2, currentColor, isFilled, isDotted);
                    break;
                case "Oval":
                    shape = new Oval(currx1, curry1, currx2, curry2, currentColor, isFilled, isDotted);
                    break;
                case "Eraser":
                    shape = new Rectangle(currx2 - 5, curry2 - 5, currx2 + 5, curry2 + 5, Color.WHITE, true, false);
                    break;
                case "Free Hand":
                    shape = new Line(prevx, prevy, currx2, curry2, currentColor, false, false);
                    break;
            }

            if (shape.isFilled) {
                shape.fill(g2d);
            } else if (shape.isDotted) {
                shape.drawDotted(g2d);
            } else {
                shape.draw(g2d);
            }

        }

    }

}
