package pack.java_samu;

import pack.java_samu.NLP;
import pack.java_samu.VisualImagery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;

public class Samu {

	public Samu() {
		mt = new ReentrantLock();
		vi = new VisualImagery();
		nlp = new NLP();
		caregiver_name_ = new ArrayList<String>();
		caregiver_name_.add("caregiver1");
		caregiver_name_.add("caregiver2");
	}

	public void destroy() {
		run_ = false;
	}

	public boolean run() {
		return run_;
	}

	public void FamilyCaregiverShell() {
		String cmd_prefix = "___";

		  System.out.print(Caregiver() + "@Caregiver> ");

		  BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));
		  try {
			for ( String line; (line = stdin_reader.readLine()) != null ; )
			    {
			      if (line.substring(0, cmd_prefix.length()).equals(cmd_prefix))
			        {
			          if ( line == cmd_prefix )
			            kovCaregiver();
			        }
			      else
			        {
			          viworker(line);
			        }

				  System.out.print(Caregiver() + "@Caregiver> ");
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		    
		    run_ = false; 
	}

	public void cmd() {
		mt.lock();

		FamilyCaregiverShell();
		mt.unlock();
	}

	public void viworker(String sentence) {
		vi.working(nlp.tirper(sentence));
	}

	public String Caregiver() {
		if (caregiver_name_.size() > 0)
			return caregiver_name_.get(caregiver_idx_);
		else
			return "Undefined";
	}

	public void kovCaregiver() {
		caregiver_idx_ = (caregiver_idx_ + 1) % caregiver_name_.size();
	}

	public double jutalom() {
		return vi.jutalom();
	}

	public boolean run_ = true;
	public Lock mt;
	public NLP nlp;
	public VisualImagery vi;
	public int caregiver_idx_ = 0;
	public List<String> caregiver_name_;

}
