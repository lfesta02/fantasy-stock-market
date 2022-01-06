# My Personal Project - Fantasy Stock Market

## Proposal

My idea for this term project is to create an imaginary, simulated stock market that can be interacted with. The user
will play the role of an investor - they will be given a starting amount of cash, from which they can buy stocks and try
to make a profit. Of course, it won't be very realistic: for one, it will be *much*, *much* simpler than the actual
market. Both the size of the market and the investor's abilities will be of a smaller scale. Also, I plan to make the
fantasy market generally more volatile than real life, mostly because I think it'll be more interesting. However, there
will still be certain stocks that are much more stable than others, similar to real life.

For the most part this software will be used for entertainment ; I think there are much better choices if someone wanted
to really learn about investing and the stock market. However, I suppose it could be a decent introduction to the basic
concept of investing because of how simple it will be. I decided on this idea mainly because I like the concept of
simulating something. I have played a few games involving simulation, and I think it's cool to be able to recreate
events without having to undertake the risk or stress of actually doing them. And although I'm a complete beginner when
it comes to investing, I find the stock market quite intriguing and thought it would be a fun thing to simulate.

## User Stories

- As a user, I want to be able to purchase and sell stocks and have my balance reflect whichever action I take
- As a user, I want to be able to simulate the instant passing of a day, and have the market update each "day"
- As a user, I want to be able to add a stock to my portfolio, where I can view all stocks I currently own
- As a user, I want to be able to view information on a stock, namely its current price and change from last day
- As a user, I want to be able to save the current state of my simulation to file when I quit
- As a user, I want to be able to resume the simulation where I left off when I reopen it from file

## Phase 4: Task 2

Wed Nov 24 10:22:17 PST 2021\
Stock: Fraser Foods Incorporated listed on stock market\
Wed Nov 24 10:22:17 PST 2021\
Stock: Burger Prince Restaurants listed on stock market\
Wed Nov 24 10:22:17 PST 2021\
Stock: Neptune Spacecraft listed on stock market\
Wed Nov 24 10:22:17 PST 2021\
Stock: Super Steroid Startup listed on stock market\
Wed Nov 24 10:22:17 PST 2021\
Stock: Beyond Food Nutrition Pills listed on stock market\
Wed Nov 24 10:22:23 PST 2021\
Stock: Beyond Food Nutrition Pills bought to portfolio\
Wed Nov 24 10:22:27 PST 2021\
Stock: Fraser Foods Incorporated bought to portfolio\
Wed Nov 24 10:22:28 PST 2021\
Stock: Fraser Foods Incorporated price updated\
Wed Nov 24 10:22:28 PST 2021\
Stock: Burger Prince Restaurants price updated\
Wed Nov 24 10:22:28 PST 2021\
Stock: Neptune Spacecraft price updated\
Wed Nov 24 10:22:28 PST 2021\
Stock: Super Steroid Startup price updated\
Wed Nov 24 10:22:28 PST 2021\
Stock: Beyond Food Nutrition Pills price updated\
Wed Nov 24 10:22:32 PST 2021\
Stock: Beyond Food Nutrition Pills sold from portfolio\
Wed Nov 24 10:22:36 PST 2021\
Stock: Burger Prince Restaurants bought to portfolio

## Phase 4: Task 3

- I would create some sort of interface or abstract class for the MarketUI and PortfolioUI classes, as 
  they share similar behaviour and structure
- I would try to find a way to get rid of the associations coming from the DashboardUI class - perhaps through
  an Observer pattern

test