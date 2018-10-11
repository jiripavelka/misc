import java.util.*;

public class TreasureHunting {

  static int prevX, prevY, nowX, nowY, trueX, trueY;
  static final int CLOSE = 3;
  static final int FAR = 6;

  /**
   * initial message
   */
  public static void openingMessage()
  {
    System.out.print(
        "#######################################################\n"
      + "# A treasure has been hidden at a location in a 10x10 #\n"
      + "# gird. Guess where it is. You have 10 chances.       #\n"
      + "#######################################################\n"
    );
  }

  /**
   * checking the distance
   */
  public static void distanceHint(int distance)
  {
    if ( distance == 0 )
    {
      System.out.println( "You have found the treasure!" );
    }
    else if ( distance <= CLOSE )
    {
      System.out.println( "The distance is no more than " + CLOSE + "." );
    }
    else if ( distance <= FAR )
    {
      System.out.println( "The distance is no more than " + FAR + "." );
    }
    else
    {
      System.out.println( "The distance is more than " + FAR + "." );
    }
  }

  public static void show(Boolean showTreasure)
  {
    for ( int y = 10; y >= 1; y -- )
    {
      for ( int x = 1; x <= 10; x ++ )
      {
        if ( showTreasure && x == trueX && y == trueY)
        {
          System.out.print( "#" ); // optional
        }
        else if ( x == nowX && y == nowY )
        {
          System.out.print( "@" );
        }
        else if ( x == prevX && y == prevY )
        {
          System.out.print( "P" );
        }
        else
        {
          System.out.print( "." );
        }
        System.out.print( " " );
      }
      System.out.println( y );
    }
    System.out.println( "@ = current, P = previous" );
  }

  public static void advise(int currDistance, int prevDistance)
  {
    if (currDistance == 0) return;
    int difference = currDistance - prevDistance;
    if ( difference < 0 )
    {
      System.out.println( "You are closer." );
    }
    else if ( difference > 0 )
    {
      System.out.println( "You are farther." );
    }
    else
    {
      System.out.println( "The same distance." );
    }
  }

  public static void main( String[] args ) {
    Scanner keyboard = new Scanner( System.in );
    int currDistance, prevDistance;

    openingMessage();
    nowX = -1;
    nowY = -1;
    currDistance = -1;
    trueX = (int)( Math.random() * 10 ) + 1;
    trueY = (int)( Math.random() * 10 ) + 1;
    System.out.println( "Treasure location: " + trueX + ", " + trueY );
    for ( int i = 1; i <= 10; i ++ )
    {
      System.out.println();
      System.out.println( "---- Round " + i + " ----" );
      prevX = nowX;
      prevY = nowY;
      prevDistance = currDistance;

      System.out.print( "Enter your guess for X, Y: " );
      nowX = keyboard.nextInt();
      nowY = keyboard.nextInt();
      // TODO: validate input

      currDistance = Math.abs( trueX - nowX ) + Math.abs( trueY - nowY );
      distanceHint(currDistance);
      if (i > 1) {
        advise(currDistance, prevDistance); // optional
      }
      show(i == 10 || currDistance == 0);
      if(currDistance == 0)
      {
        break;
      }
    }
  }
}
