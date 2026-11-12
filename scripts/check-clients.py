"""Repeatable examples of valid and invalid client input."""
import os
import subprocess


def run(program, commands, expected):
    result = subprocess.run(['java', '-ea', '-cp', 'bin' + os.pathsep + 'lib/*',
                             program], input=commands, text=True,
                            capture_output=True, check=True, timeout=10)
    assert not result.stderr, result.stderr
    for line in expected:
        assert line in result.stdout, (program, line, result.stdout)
    print(program + ': PASS')


run('BookingDemo', 'reserve 1 2\nreserve 1 2\nreserve 3 4\nrelease 1 2\nshow\nquit\n',
    ['Reserved.', 'Seat is already reserved.', 'Released.',
     '3 x 4 [OOOO / OOOO / OOOX]', 'Available: 11'])
run('BookingDemo', 'reserve 0 1\nreserve x 1\nreserve 99999999999999 1\n'
    'reserve 1\nrelease 1 1\nwhat\nshow\n',
    ['Invalid seat.', 'Coordinates must be integers.',
     'Enter exactly two coordinates.', 'Seat is already available.',
     'Unknown command.', 'Available: 12'])
run('BookingDemo', '', [])
run('CheckInDemo', '',
    ['Passenger ' + str(n) + ': row ' + str((n - 1) // 3 + 1)
     + ', seat ' + str((n - 1) % 3 + 1) for n in range(1, 7)]
    + ['Passenger 7: flight full', 'Remaining: 0'])
