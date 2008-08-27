#!/usr/bin/env python

"""
 \file MyService2_idl_examplefile.py
 \brief Python example implementations generated from MyService2.idl
 \date $Date$


"""

import CORBA, PortableServer

# Import the Python stub modules so type definitions are available.

import _GlobalIDL

# Import the Python Skeleton modules so skeleton base classes are available.

import _GlobalIDL__POA


class MyService2_i (_GlobalIDL__POA.MyService2):
    """
    \class MyService2_i
    Example class implementing IDL interface MyService2
    """

    def __init__(self):
        """
        \brief standard constructor
        Initialise member variables here
        """
        pass

    # void setKpGain(in float gainkp)
    def setKpGain(self, gainkp):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: None

    # float getKpGain()
    def getKpGain(self):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result


if __name__ == "__main__":
    import sys
    
    # Initialise the ORB
    orb = CORBA.ORB_init(sys.argv)
    
    # As an example, we activate an object in the Root POA
    poa = orb.resolve_initial_references("RootPOA")

    # Create an instance of a servant class
    servant = MyService2_i()

    # Activate it in the Root POA
    poa.activate_object(servant)

    # Get the object reference to the object
    objref = servant._this()
    
    # Print a stringified IOR for it
    print orb.object_to_string(objref)

    # Activate the Root POA's manager
    poa._get_the_POAManager().activate()

    # Run the ORB, blocking this thread
    orb.run()

