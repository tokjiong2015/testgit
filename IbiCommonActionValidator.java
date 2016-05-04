
package sg.com.kpmg.factorpro.web.ibi.common.action;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import sg.com.kpmg.factorpro.common.ResourceKeys;
import sg.com.kpmg.factorpro.model.YesNoInd;
import sg.com.kpmg.factorpro.model.cntl.Cntl;
import sg.com.kpmg.factorpro.model.cntl.CntlKey;
import sg.com.kpmg.factorpro.web.common.action.FactorProAction;
import sg.com.kpmg.factorpro.web.ibi.common.handler.IbiCommonActionHandler;

public class IbiCommonActionValidator
{

	public boolean validatePaymentMode(String pymtMode, String chqNo, String currentAccount, IbiCommonActionHandler handler)
	{
		// If cheque payment mode, must have cheque no. Else, cheque no. must be empty.
		Cntl chequeIndCntl = handler.getCntlByPrimaryKey(CntlKey.CTL_TYPE_F4, pymtMode, CntlKey.CTL_PARAM_CHQ_IND);
		if(YesNoInd.Y.name().equals(chequeIndCntl.getCtlValue()))
		{
			if(StringUtils.isEmpty(chqNo))
				action.addActionError( action.getText(ResourceKeys.FMU0230.name()) );
		}else
		{
			if(!StringUtils.isEmpty(chqNo))
				action.addActionError( action.getText(ResourceKeys.FMU0231.name()) );
		}

	}
<<<<<<< Updated upstream
	
	public void printLnMethod1()
	{
		System.out.println("helloWorld");
	}
=======

	public void printLnMethod2()
	{
	    System.out.println("testing");
	}

>>>>>>> Stashed changes
}
