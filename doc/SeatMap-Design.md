# SeatMap design

## Scope and model

The abstract value is (rows, seatsPerRow, reserved coordinates). Both
positive dimensions multiply to at most Integer.MAX_VALUE. Coordinates start
at one. Constructors produce an empty reservation set. Kernel updates affect
only their specified seat; query methods preserve the entire value.

## Layers

SeatMapKernel extends Standard<SeatMap> and defines rows, seatsPerRow,
isAvailable, reserve, and release. SeatMap extends it with countAvailable
and isFull. SeatMapSecondary implements these queries, equals, hashCode,
and toString entirely through kernel methods. SeatMap1 supplies all kernel
and Standard methods. It is final, so newInstance can construct SeatMap1
while preserving the dynamic type.

## Representation

SeatMap1 owns an OSU Set<Integer>, implemented by Set1L, plus two dimension
fields. A reserved coordinate (r,s) is encoded as (r-1)*seatsPerRow+s-1.
Only indexes in the map's capacity range belong to the set. The convention
and correspondence are documented in the source. Sparse storage avoids
allocating a boolean array for every possible seat. Seat updates use set
membership, addition, and removal. The secondary layer never sees this set.

## Initial value and transfer

The no-argument initial value is (1,1,{}). clear restores it even if the map
was constructed with different dimensions. newInstance creates a fresh map
in that state. transferFrom replaces dimensions and reservations, then gives
the source a new empty set and default dimensions. Ownership is moved, not
shared. Matching dimensions are not a transfer precondition.

## Overflow and cost

The constructor multiplies after conversion to long to check capacity.
Valid indexes fit in int. Secondary loops count from zero with strict upper
bounds, converting to one-based coordinates for kernel calls. This avoids
incrementing a one-based loop counter beyond Integer.MAX_VALUE. Hash
arithmetic intentionally permits Java integer wraparound.

Derived operations scan the complete layout. toString uses O and X in
row order, with slashes between rows. This is practical for aircraft-sized
maps, but intentionally not optimized for huge dimensions.

## Reference conventions

The ExposureAssist repository supplied a formatting and organization example:
a component package, separate kernel/enhanced interfaces, a secondary class,
assertion checks, explicit loops, and short contracts. SeatMap follows those
conventions while retaining the common methods required by its own rubric.
It does not reuse the reference's exposure calculations or source text.
