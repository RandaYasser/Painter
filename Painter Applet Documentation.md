The **Painter Applet** is a Java-based drawing application that allows users to create and manipulate shapes on a canvas. It provides a variety of tools for drawing lines, rectangles, ovals, freehand sketches, and erasing. Additionally, it supports features like saving and loading drawings, undo functionality, and customizable drawing styles (solid or dotted lines, filled shapes).

This documentation provides an overview of the project, its structure, and how to use the application.

------------------------------------------------------------------------

## Table of Contents

1.  [Features](#features)
2.  [Project Structure](#project-structure)
    - [Shape Class](#shape-class)
    - [Line, Rectangle, and Oval Classes](#line-rectangle-and-oval-classes)
    - [Painter Applet](#painter-applet)
3.  [How to Use](#how-to-use)
    - [Drawing Shapes](#drawing-shapes)
    - [Using Tools](#using-tools)
    - [Saving and Loading Drawings](#saving-and-loading-drawings)
4.  [How to Run](#how-to-run)

------------------------------------------------------------------------

## Features

- **Drawing Tools**:
  - Line
  - Rectangle
  - Oval
  - Free Hand (sketching)
  - Eraser
- **Customization**:
  - Choose colors (Red, Blue, Green)
  - Toggle between solid and dotted lines
  - Fill shapes with color
- **Functionality**:
  - Undo last action
  - Clear the entire canvas
  - Save and load drawings
- **User Interface**:
  - Buttons for tools and actions
  - Checkboxes for fill and dotted line options

------------------------------------------------------------------------

## Project Structure

### Shape Class

The `Shape` class is an abstract class that serves as the parent class for all shapes. It defines common properties and methods for shapes, such as:
- Coordinates (`x1`, `y1`, `x2`, `y2`)
- Color (`color`)
- Fill status (`isFilled`)
- Dotted line status (`isDotted`)

It also declares abstract methods:
- `draw(Graphics2D g)`: Draws the shape.
- `drawDotted(Graphics2D g)`: Draws the shape with a dotted line.
- `fill(Graphics2D g)`: Fills the shape with color.

### Line, Rectangle, and Oval Classes

These classes extend the `Shape` class and implement the `draw`, `drawDotted`, and `fill` methods for their respective shapes:
- **Line**: Draws a straight line.
- **Rectangle**: Draws a rectangle (filled or outlined).
- **Oval**: Draws an oval (filled or outlined).

### Painter Applet

The `Painter` class is the main applet that handles the user interface, mouse events, and drawing logic. It includes:
- Buttons for tools and actions.
- Checkboxes for fill and dotted line options.
- Mouse listeners for drawing and erasing.
- Methods for saving and loading drawings.

------------------------------------------------------------------------

## How to Use

### Drawing Shapes

1.  **Select a Shape**:
    - Click the "Line", "Rectangle", or "Oval" button to choose a shape.
2.  **Choose a Color**:
    - Click the "Red", "Blue", or "Green" button to set the drawing color.
3.  **Draw**:
    - Click and drag on the canvas to draw the selected shape.

### Using Tools

- **Free Hand**:
  - Click the "Free Hand" button to sketch freely on the canvas.
- **Eraser**:
  - Click the "Eraser" button to erase parts of the drawing.
- **Undo**:
  - Click the "Undo" button to remove the last drawn shape.
- **Clear**:
  - Click the "Clear All" button to reset the canvas.

### Saving and Loading Drawings

- **Save**:
  - Click the "Save" button to save the current drawing to a file.
- **Load**:
  - Click the "Open" button to load a previously saved drawing.

|  |
|----|
| \## How to Run |
| 1. Compile the code using `javac`. |
| 2. Launch the applet using an applet viewer or embed it in an HTML file for browser execution. |
