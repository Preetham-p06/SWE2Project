# Portfolio reflection

## Understanding software development

This project helped me understand how to organize a component into smaller
layers with clear responsibilities. I learned how the kernel provides
essential operations and how secondary methods build on those operations
without knowing how the data is stored. It also showed me why method
contracts matter, especially when deciding what a method can assume and
what it must preserve.

## Gaps and challenges

The most difficult part was working through clear() and transferFrom(). At
first, clearing a seat map seemed like it should only remove reservations.
Understanding the inherited Standard contract made me think more carefully
about the initial value, dimensions, and what happens to the source after a
transfer. Testing also pushed me to check whether every other seat stayed
unchanged, rather than only checking the seat I updated.

## Perspective

I still enjoy software development and aviation. I like taking a real
situation and figuring out how to represent it in code. The seat map gave
me a connection between those interests.

## Skills

I learned more about separating a mathematical model from its storage,
organizing methods into layers, and reasoning about preconditions and
preserved state. Testing made me consider the full state of a component,
including what should remain unchanged after an operation.

## Resume wording

- Practiced layered Java component design with mathematical contracts.
- Analyzed reset and transfer behavior for an aircraft seating model.
- Developed an understanding of systematic state-transition testing.

## Career interests

I remain interested in software development and aviation. I would like to
explore how software supports airline operations and other aviation systems.
This project gave me a small example of how those interests can connect.

## Next steps

Moving forward, I want to strengthen my Java, data structures, and testing
skills through more projects and internships. A possible next step is to
ask a course instructor or grader for feedback on my reasoning and seek
mentorship from people working on aviation software. These are potential
contacts rather than people I have already consulted.
