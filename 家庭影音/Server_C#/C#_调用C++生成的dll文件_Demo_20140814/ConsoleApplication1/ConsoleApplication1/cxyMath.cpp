// ConsoleApplication1.cpp : ���� DLL Ӧ�ó���ĵ���������
//

#include "stdafx.h"
#include "cxyMath.h"

extern "C" _declspec(dllexport) double MyMathFuncs::Add(double a, double b){
	return a + b;
}

extern "C" _declspec(dllexport) double MyMathFuncs::Subtract(double a, double b){
	return a - b;
}

extern "C" _declspec(dllexport) double MyMathFuncs::Multiply(double a, double b){
	return a * b;
}

extern "C" _declspec(dllexport) double MyMathFuncs::Divide(double a, double b){
	return a / b;
}