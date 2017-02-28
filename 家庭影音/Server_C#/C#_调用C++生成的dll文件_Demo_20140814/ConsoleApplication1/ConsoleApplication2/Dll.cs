using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication2
{
    class cxyMath
    {
        [DllImport(@"c:\users\ray\documents\visual studio 2013\Projects\ConsoleApplication1\Debug\ConsoleApplication1.dll", EntryPoint = "?Divide@MyMathFuncs@@SANNN@Z")]
        public static extern double Divide(double a, double b);
    }
}
