#!/usr/bin/env python

"""
 \file MyService_idl_examplefile.py
 \brief Python example implementations generated from MyService.idl
 \date $Date$


"""

import CORBA, PortableServer

# Import the Python stub modules so type definitions are available.

import _GlobalIDL

# Import the Python Skeleton modules so skeleton base classes are available.

import _GlobalIDL__POA


class MyService_i (_GlobalIDL__POA.MyService):
    """
    \class MyService_i
    Example class implementing IDL interface MyService
    """

    def __init__(self):
        """
        \brief standard constructor
        Initialise member variables here
        """
        pass

    # short echo01(in short ss, out short ss2, inout short ss3)
    def echo01(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # long echo02(in long ss, out long ss2, inout long ss3)
    def echo02(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # long long echo03(in long long ss, out long long ss2, inout long long ss3)
    def echo03(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # unsigned long echo04(in unsigned long ss, out unsigned long ss2, inout unsigned long ss3)
    def echo04(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # unsigned long long echo05(in unsigned long long ss, out unsigned long long ss2, inout unsigned long long ss3)
    def echo05(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # unsigned short echo06(in unsigned short ss, out unsigned short ss2, inout unsigned short ss3)
    def echo06(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # float echo07(in float ss, out float ss2, inout float ss3)
    def echo07(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # double echo08(in double ss, out double ss2, inout double ss3)
    def echo08(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # char echo10(in char ss, out char ss2, inout char ss3)
    def echo10(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # wchar echo11(in wchar ss, out wchar ss2, inout wchar ss3)
    def echo11(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # octet echo12(in octet ss, out octet ss2, inout octet ss3)
    def echo12(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # boolean echo13(in boolean ss, out boolean ss2, inout boolean ss3)
    def echo13(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # string echo14(in string ss, out string ss2, inout string ss3)
    def echo14(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # wstring echo15(in wstring ss, out wstring ss2, inout wstring ss3)
    def echo15(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3

    # any echo16(in any ss, out any ss2, inout any ss3)
    def echo16(self, ss, ss3):
        raise CORBA.NO_IMPLEMENT(0, CORBA.COMPLETED_NO)
        # *** Implement me
        # Must return: result, ss2, ss3


if __name__ == "__main__":
    import sys
    
    # Initialise the ORB
    orb = CORBA.ORB_init(sys.argv)
    
    # As an example, we activate an object in the Root POA
    poa = orb.resolve_initial_references("RootPOA")

    # Create an instance of a servant class
    servant = MyService_i()

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

